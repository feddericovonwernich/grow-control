package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.repository.GrowStageRepository;
import com.fg.grow_control.service.GrowStageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growStage")
@PreAuthorize("permitAll()")
public class GrowStageController extends BasicController<GrowStage, Long, GrowStageRepository, GrowStageService> {
    public GrowStageController(GrowStageService service) {
        super(service);
    }
}
