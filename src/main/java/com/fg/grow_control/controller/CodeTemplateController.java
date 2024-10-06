package com.fg.grow_control.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fg.grow_control.entity.CodeTemplate;
import com.fg.grow_control.repository.CodeTemplateRepository;
import com.fg.grow_control.service.CodeTemplateService;

@RestController
@RequestMapping("/ardino-code-template")
@PreAuthorize("permitAll()")
public class CodeTemplateController extends BasicController<CodeTemplate, Long, CodeTemplateRepository, CodeTemplateService> {

    public CodeTemplateController(CodeTemplateService service) {
        super(service);
    }
}
