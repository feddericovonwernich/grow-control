package com.fg.grow_control.controller;

import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.repository.OptimalGrowingParameterRepository;
import com.fg.grow_control.service.OptimalGrowingParameterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/optimalGrowingParameter")
public class OptimalGrowingParameterController extends BasicController<OptimalGrowingParameter, Long, OptimalGrowingParameterRepository, OptimalGrowingParameterService> {
    public OptimalGrowingParameterController(OptimalGrowingParameterService service) {
        super(service);
    }
}
