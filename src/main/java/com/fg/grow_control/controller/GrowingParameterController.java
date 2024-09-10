package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import com.fg.grow_control.service.GrowingParameterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/growingParameter")
@PreAuthorize("permitAll()")
public class GrowingParameterController extends BasicController<GrowingParameter, Long, GrowingParameterRepository, GrowingParameterService> {
    public GrowingParameterController(GrowingParameterService service) {
        super(service);
    }
}
