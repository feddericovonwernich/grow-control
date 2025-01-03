package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowCycleSettingValue;
import com.fg.grow_control.repository.GrowCycleSettingValueRepository;
import com.fg.grow_control.service.GrowCycleSettingValueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growCycleSettingValue")
@PreAuthorize("permitAll()")
public class GrowCycleSettingValueController extends BasicController<GrowCycleSettingValue, Long,
        GrowCycleSettingValueRepository, GrowCycleSettingValueService> {

    public GrowCycleSettingValueController(GrowCycleSettingValueService service) {
        super(service);
    }
}
