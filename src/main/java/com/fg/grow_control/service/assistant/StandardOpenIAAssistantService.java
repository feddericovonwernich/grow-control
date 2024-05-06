package com.fg.grow_control.service.assistant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.ListSearchParameters;
import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.assistants.*;
import com.theokanning.openai.assistants.AssistantRequest.AssistantRequestBuilder;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageContent;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.messages.content.Text;
import com.theokanning.openai.runs.*;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.Thread;
import com.theokanning.openai.threads.ThreadRequest;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.theokanning.openai.utils.TikTokensUtil.ModelEnum.valueOf;

@Service
@Conditional(AssistantEnabledCondition.class)
public class StandardOpenIAAssistantService implements AssistantService {

    private static final Logger log = LoggerFactory.getLogger(StandardOpenIAAssistantService.class);

    @Value("${assistant.name}")
    private String assistantName;

    @Value("${assistant.openia.model}")
    private String assistantModel;

    @Value("${assistant.description}")
    private String assistantDescription;

    @Value("${assistant.prompt}")
    private String assistantPrompt;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private OpenAiService service;

    private Assistant assistant;

    private ThreadLocal<Boolean> assistantFailed = new ThreadLocal<>();

    @PostConstruct
    private void init() {
        assistant = fetchAssistants();

        // Temporal hack, to debug tool creation.
        if (assistant != null) {
            service.deleteAssistant(assistant.getId());
            log.info("Deleted assistant with id: " + assistant.getId());
            assistant = null;
        }

        if (assistant == null) {
            assistant = createAssistant();
        }
    }


    @Override
    public String processRequest(String userInput) {
        if (assistant == null) {
            throw new RuntimeException("Unable to get an assistant.");
        }
        // Create the thread to execute the user request.
        Thread thread = createThread();
        MDC.put("threadId", thread.getId());
        return processRequestOnThread(userInput, thread);
    }

    @Override
    public String processRequest(String userInput, String threadId) {
        if (assistant == null) {
            throw new RuntimeException("Unable to get an assistant.");
        }
        // Get the thread where it was executing.
        Thread thread = getThread(threadId);
        if (thread != null) {
            return processRequestOnThread(userInput, thread);
        } else {
            return "Non-existent thread. Use a valid thread id.";
        }
    }

    private String processRequestOnThread(String userInput, Thread thread) {
        // Append user request to thread.
        createMessageOnThread(userInput, thread);

        // Assign the thread to the assistant.
        Run run = createRunForThread(thread);
        Run retrievedRun = service.retrieveRun(thread.getId(), run.getId());
        retrievedRun = waitForRun(thread, run, retrievedRun);

        processActions(retrievedRun, thread, run);

        if (assistantFailed.get() != null && assistantFailed.get()) {
            return "Request was sent, but assistant failed to process it.";
        }

        // Get the response.
        OpenAiResponse<Message> response = service.listMessages(thread.getId());

        // Before extracting the message text, ensure that the response and its content are not null
        Optional<String> messageText = response.getData().stream()
                .findFirst()
                .map(Message::getContent)
                .flatMap(content -> content.stream().findFirst())
                .map(MessageContent::getText)
                .map(Text::getValue);

        return messageText.orElse("Error: Couldn't process request.");
    }

    private Thread getThread(String threadId) {
        try {
            return service.retrieveThread(threadId);
        } catch (OpenAiHttpException ex) {
            if (ex.statusCode == 404) {
                return null;
            } else {
                throw new RuntimeException("Error while retrieving thread.", ex);
            }
        }
    }

    private void processActions(Run retrievedRun, Thread thread, Run run) {
        if (retrievedRun.getStatus().equals("requires_action")) {
            List<SubmitToolOutputRequestItem> toolOutputRequestItems = getSubmitToolOutputRequestItems(retrievedRun);
            SubmitToolOutputsRequest submitToolOutputsRequest = SubmitToolOutputsRequest.builder()
                    .toolOutputs(toolOutputRequestItems)
                    .build();
            retrievedRun = service.submitToolOutputs(retrievedRun.getThreadId(), retrievedRun.getId(), submitToolOutputsRequest);
            retrievedRun = waitForRun(thread, run, retrievedRun);
            processActions(retrievedRun, thread, run);
        }
        if (retrievedRun.getStatus().equals("failed")) {
            log.error(retrievedRun.getLastError().getMessage());
            assistantFailed.set(true);
        }
    }

    @NotNull
    private List<SubmitToolOutputRequestItem> getSubmitToolOutputRequestItems(Run retrievedRun) {
        RequiredAction requiredAction = retrievedRun.getRequiredAction();
        List<ToolCall> toolCalls = requiredAction.getSubmitToolOutputs().getToolCalls();
        List<SubmitToolOutputRequestItem> toolOutputRequestItems = new ArrayList<>();
        toolCalls.forEach(toolCall -> {
            String functionCallResponse = executeFunctionCall(toolCall.getFunction());
            SubmitToolOutputRequestItem toolOutputRequestItem = SubmitToolOutputRequestItem.builder()
                    .toolCallId(toolCall.getId())
                    .output(functionCallResponse)
                    .build();
            toolOutputRequestItems.add(toolOutputRequestItem);
        });
        return toolOutputRequestItems;
    }

    private Run createRunForThread(Thread thread) {
        RunCreateRequest runCreateRequest = RunCreateRequest.builder()
                .assistantId(assistant.getId())
                .build();
        return service.createRun(thread.getId(), runCreateRequest);
    }

    private void createMessageOnThread(String userInput, Thread thread) {
        MessageRequest messageRequest = MessageRequest.builder()
                .content(userInput)
                .build();
        service.createMessage(thread.getId(), messageRequest);
    }

    private Thread createThread() {
        ThreadRequest threadRequest = ThreadRequest.builder().build();
        return service.createThread(threadRequest);
    }

    @NotNull
    private Run waitForRun(Thread thread, Run run, Run retrievedRun) {
        while (!(retrievedRun.getStatus().equals("completed"))
                && !(retrievedRun.getStatus().equals("failed"))
                && !(retrievedRun.getStatus().equals("requires_action"))) {
            retrievedRun = service.retrieveRun(thread.getId(), run.getId());
        }
        return retrievedRun;
    }

    private String executeFunctionCall(ToolCallFunction function) {
        Map<String, Object> beans = appContext.getBeansWithAnnotation(AssistantToolProvider.class);
        AtomicReference<String> functionResponse = new AtomicReference<>("");
        beans.values().forEach(bean -> {
            if (bean instanceof ToolParameterAware toolParamAwareBean) {
                Class<?> beanClass = toolParamAwareBean.getClass().getSuperclass();
                Method[] methods = beanClass.getDeclaredMethods();
                for (Method method : methods) {
                    FunctionDefinition functionDefinition = method.getDeclaredAnnotation(FunctionDefinition.class);
                    if (functionDefinition != null && functionDefinition.name().equals(function.getName())) {
                        log.info("Function arguments: " + function.getArguments());
                        List<Object> arguments = toolParamAwareBean.getParametersForFunction(function.getName(), function.getArguments());
                        try {
                            Object result = method.invoke(toolParamAwareBean, arguments.toArray());
                            log.info("Execution result: " + result);
                            functionResponse.set(result.toString());
                        } catch (Exception e) {
                            log.error("Error during function execution: {}", e.getMessage());
                            if (e instanceof InvocationTargetException targetException) {
                                functionResponse.set(targetException.getTargetException().getMessage());
                            }
                        }

                        // Have to break here, or it's going to execute twice,
                        //  as methods list has 2 entries for each defined method.
                        break;
                    }
                }
            } else {
                log.error("Configuration error. Bean of instance {} should implement ToolParameterAware interface.", bean.getClass().getSuperclass());
                functionResponse.set("Failed to execute function call. Configuration error.");
            }
        });

        return functionResponse.get();
    }

    private Assistant fetchAssistants() {
        OpenAiResponse<Assistant> response = service.listAssistants(new ListSearchParameters());
        List<Assistant> assistantList = response.getData();
        for (Assistant assistant : assistantList) {
            if (assistant.getName().equals(assistantName)) {
                log.info("Assistant found for name: " + assistantName);
                return assistant;
            }
        }
        log.info("Assistant not found.");
        return null;
    }

    private Assistant createAssistant() {
        List<Tool> toolList = getTools();

        try {
            AssistantRequestBuilder requestBuilder = AssistantRequest.builder()
                    .model(valueOf(assistantModel).getName())
                    .description(assistantDescription)
                    .name(assistantName)
                    .instructions(assistantPrompt)
                    .tools(toolList);

            Assistant assistant = service.createAssistant(requestBuilder.build());
            log.info("Created assistant successfully wit ID: " + assistant.getId());
            return assistant;
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Illegal model: " + assistantModel);
        }
    }

    private List<Tool> getTools() {
        List<Tool> toolList = new ArrayList<>();
        List<AssistantFunction> functions = new ArrayList<>();
        Set<String> addedFunctions = new HashSet<>();
        Map<String, Object> beans = appContext.getBeansWithAnnotation(AssistantToolProvider.class);

        beans.values().forEach(bean -> {
            Class<?> beanClass = bean.getClass().getSuperclass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                FunctionDefinition functionDefinition = method.getDeclaredAnnotation(FunctionDefinition.class);
                if (functionDefinition != null) {
                    AssistantFunction assistantFunction;
                    try {
                        assistantFunction = AssistantFunction.builder()
                                .name(functionDefinition.name())
                                .description(functionDefinition.description())
                                .parameters(buildParameters(functionDefinition.parameters()))
                                .build();
                    } catch(JsonProcessingException e) {
                        throw new RuntimeException("Error while creating assistant tools during parameter processing. Details: " + e.getMessage(), e);
                    }

                    // This is here to avoid adding some methods are listed twice.
                    if (!addedFunctions.contains(assistantFunction.getDescription())) {
                        functions.add(assistantFunction);
                        addedFunctions.add(assistantFunction.getDescription());
                    } else {
                        log.info("Not adding duplicate function. Description: " + assistantFunction.getDescription());
                    }
                }
            }
        });

        functions.forEach(assistantFunction -> {
            toolList.add(new Tool(AssistantToolsEnum.FUNCTION, assistantFunction));
        });

        return toolList;
    }

    private Map<String, Object> buildParameters(String parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(parameters, new TypeReference<>() {});
    }

}
