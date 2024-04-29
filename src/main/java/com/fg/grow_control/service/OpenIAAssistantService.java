package com.fg.grow_control.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.assistants.*;
import com.theokanning.openai.assistants.AssistantRequest.AssistantRequestBuilder;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.*;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.Thread;
import com.theokanning.openai.threads.ThreadRequest;
import com.theokanning.openai.utils.TikTokensUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO Make this service registration depend on configuration.
@Service
public class OpenIAAssistantService {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    OpenAiService service;

    private Assistant assistant;

    public String processRequest(String userInput) {
        if (assistant == null) {
            assistant = fetchAssistants();
            if (assistant == null) {
                assistant = createAssistant();
            }
        }
        if (assistant == null) {
            throw new RuntimeException("Unable to get an assistant.");
        }

        // Create the thread to execute the user request.
        Thread thread = createThread();

        // Append user request to thread.
        createMessageOnThread(userInput, thread);

        // Assign the thread to the assistant.
        Run run = createRunForThread(thread);

        Run retrievedRun = service.retrieveRun(thread.getId(), run.getId());

        retrievedRun = waitForRun(thread, run, retrievedRun);

        if (retrievedRun.getStatus().equals("requires_action")) {

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

            SubmitToolOutputsRequest submitToolOutputsRequest = SubmitToolOutputsRequest.builder()
                    .toolOutputs(toolOutputRequestItems)
                    .build();

            retrievedRun = service.submitToolOutputs(retrievedRun.getThreadId(), retrievedRun.getId(), submitToolOutputsRequest);

            retrievedRun = waitForRun(thread, run, retrievedRun);

            // TODO Here we need to check if more actions are required..

            OpenAiResponse<Message> response = service.listMessages(thread.getId());

            List<Message> messages = response.getData();

            // TODO Format the response to return to the user!
        }

        // TODO Implement how this is supposed to call functions.
        return null;
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

        beans.values().forEach(bean -> {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                FunctionDefinition functionDefinition = method.getAnnotation(FunctionDefinition.class);
                if (functionDefinition != null && functionDefinition.name().equals(function.getName())) {
                    // TODO need to invoke method with parameters from function.
                }
            }
        });

        // TODO Stringify response.
        return null;
    }

    private Assistant fetchAssistants() {
        // TODO Implement.
        return null;
    }

    private Assistant createAssistant() {
        // TODO Complete this with something that makes sense.
        AssistantRequestBuilder requestBuilder = AssistantRequest.builder()
                .model(TikTokensUtil.ModelEnum.GPT_4.getName()) // TODO Investigate which model fits best here and make it configurable
                .description("Service to interact with system through a text chat.")
                .name("GrowControlAssistant")
                .instructions("""
                            You are a chat interface to a system that controls growing cycles of different plants.
                            You will be given a set of tools, your task is to interpret user input, and decide which operation to perform on the system.
                        """)
                .tools(getTools());

        return service.createAssistant(requestBuilder.build());
    }

    // TODO Create a unit test for this.
    private List<Tool> getTools() {
        List<Tool> toolList = new ArrayList<>();
        List<AssistantFunction> functions = new ArrayList<>();
        Map<String, Object> beans = appContext.getBeansWithAnnotation(AssistantToolProvider.class);

        beans.values().forEach(bean -> {
            Class<?> beanClass = bean.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method method : methods) {
                FunctionDefinition functionDefinition = method.getAnnotation(FunctionDefinition.class);
                if (functionDefinition != null) {
                    AssistantFunction assistantFunction;
                    try {
                        assistantFunction = AssistantFunction.builder()
                                .name(functionDefinition.name())
                                .description(functionDefinition.description())
                                .parameters(buildParameters(functionDefinition.parameters()))
                                .build();
                    } catch (JsonProcessingException e) {
                        // TODO Improve to show where this failed.
                        throw new RuntimeException("Error while creating assistant tools.");
                    }
                    functions.add(assistantFunction);
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
        return mapper.readValue(parameters, new TypeReference<>() {
        });
    }

}
