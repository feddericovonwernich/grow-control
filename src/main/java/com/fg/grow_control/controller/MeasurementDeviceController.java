package com.fg.grow_control.controller;

import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.MeasurementDeviceRepository;
import com.fg.grow_control.service.MeasurementDeviceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurementDevice")
@PreAuthorize("permitAll()")
public class MeasurementDeviceController extends BasicController<MeasurementDevice, Long, MeasurementDeviceRepository, MeasurementDeviceService> {

    public MeasurementDeviceController(MeasurementDeviceService repository) {
        super(repository);
    }

}
