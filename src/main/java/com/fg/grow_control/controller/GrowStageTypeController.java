package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.repository.GrowStageTypeRepository;
import com.fg.grow_control.service.GrowStageTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growStageType")
public class GrowStageTypeController extends BasicController<GrowStageType, Long, GrowStageTypeRepository, GrowStageTypeService>{
    public GrowStageTypeController(GrowStageTypeService service) {
        super(service);
    }
}
