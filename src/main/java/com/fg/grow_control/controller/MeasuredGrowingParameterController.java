package com.fg.grow_control.controller;

import com.fg.grow_control.entity.MeasuredGrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import com.fg.grow_control.service.MeasuredGrowingParameterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measuredGrowingParameter")
public class MeasuredGrowingParameterController extends BasicController<MeasuredGrowingParameter, Long, GrowingParameterRepository, MeasuredGrowingParameterService> {
    public MeasuredGrowingParameterController(MeasuredGrowingParameterService service) {
        super(service);
    }
}
