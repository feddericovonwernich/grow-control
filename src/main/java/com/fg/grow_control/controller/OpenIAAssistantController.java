package com.fg.grow_control.controller;

import com.fg.grow_control.service.assistant.AssistantEnabledCondition;
import com.fg.grow_control.service.assistant.StandardOpenIAAssistantService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Conditional(AssistantEnabledCondition.class)
@RequestMapping("/assistant")
public class OpenIAAssistantController {

    @Autowired
    private StandardOpenIAAssistantService openIAAssistantService;

    @PostMapping
    public ResponseEntity<String> processRequest(@RequestBody String userRequest) {
        String assistantResponse = openIAAssistantService.processRequest(userRequest);
        return buildAssistantResponseEntity(assistantResponse);
    }

    @PostMapping("/thread/{threadId}")
    public ResponseEntity<String> processRequest(@RequestBody String userRequest, @PathVariable String threadId) {
        String assistantResponse = openIAAssistantService.processRequest(userRequest, threadId);
        return buildAssistantResponseEntity(assistantResponse);
    }

    @NotNull
    private static ResponseEntity<String> buildAssistantResponseEntity(String assistantResponse) {
        try {
            HttpHeaders responseHeaders = new HttpHeaders();
            String threadId = MDC.get("threadId");
            if (threadId != null) {
                responseHeaders.set("ThreadId", threadId);
            }
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(assistantResponse);
        } finally {
            MDC.clear();
        }
    }

}
