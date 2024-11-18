package com.fg.grow_control.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fg.grow_control.entity.Setting;
import com.fg.grow_control.repository.SettingRepository;
import com.fg.grow_control.service.SettingService;

@RestController
@RequestMapping("/setting")
@PreAuthorize("permitAll()")
public class SettingController extends BasicController<Setting, Long, SettingRepository, SettingService> {

    public SettingController(SettingService service) {
        super(service);
    }
}
