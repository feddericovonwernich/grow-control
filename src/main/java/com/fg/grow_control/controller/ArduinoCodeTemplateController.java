package com.fg.grow_control.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fg.grow_control.entity.ArduinoCodeTemplate;
import com.fg.grow_control.repository.ArduinoCodeTemplateRepository;
import com.fg.grow_control.service.ArduinoCodeTemplateService;

@RestController
@RequestMapping("/ardino-code-template")
@PreAuthorize("permitAll()")
public class ArduinoCodeTemplateController extends BasicController<ArduinoCodeTemplate, Long, ArduinoCodeTemplateRepository, ArduinoCodeTemplateService> {

    public ArduinoCodeTemplateController(ArduinoCodeTemplateService service) {
        super(service);
    }
}
