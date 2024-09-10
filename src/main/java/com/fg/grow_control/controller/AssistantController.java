package com.fg.grow_control.controller;

import io.github.feddericovonwernich.spring_ai.function_calling_service.spi.AssistantService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assistant")
@PreAuthorize("permitAll()")
@ConditionalOnBean(AssistantService.class)
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    @PostMapping
    public ResponseEntity<String> processRequest(@RequestBody String userRequest) {
        String assistantResponse = assistantService.processRequest(userRequest).getResponse();
        return buildAssistantResponseEntity(assistantResponse);
    }

    @PostMapping("/thread/{threadId}")
    public ResponseEntity<String> processRequest(@RequestBody String userRequest, @PathVariable String threadId) {
        String assistantResponse = assistantService.processRequest(userRequest, threadId);
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
