package com.fg.grow_control.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fg.grow_control.entity.DeviceType;
import com.fg.grow_control.repository.DeviceTypeRepository;
import com.fg.grow_control.service.DeviceTypeService;

@RestController
@RequestMapping("/device-type")
@PreAuthorize("permitAll()")
public class DeviceTypeController extends BasicController<DeviceType, Long, DeviceTypeRepository, DeviceTypeService> {

    public DeviceTypeController(DeviceTypeService service) {
        super(service);
    }
}