package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.repository.GrowStageRepository;
import com.fg.grow_control.service.GrowStageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/growStage")
public class GrowStageController extends BasicController<GrowStage, Long, GrowStageRepository, GrowStageService> {
    public GrowStageController(GrowStageService service) {
        super(service);
    }
}
