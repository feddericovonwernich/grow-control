package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
import com.fg.grow_control.service.GrowCycleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growCycle")
public class GrowCycleController extends BasicController<GrowCycle, Long, GrowCycleRepository, GrowCycleService> {
    public GrowCycleController(GrowCycleService service) {
        super(service);
    }
}
