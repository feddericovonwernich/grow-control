package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.repository.GrowingParameterTypeRepository;
import com.fg.grow_control.service.GrowingParameterTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growingParameterType")
public class GrowingParameterTypeController extends BasicController<GrowingParameterType, Long, GrowingParameterTypeRepository, GrowingParameterTypeService> {
    public GrowingParameterTypeController(GrowingParameterTypeService service) {
        super(service);
    }
}
