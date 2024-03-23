package com.fg.grow_control.controller;

import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.MeasurementDeviceRepository;
import com.fg.grow_control.service.BasicService;
import com.fg.grow_control.service.MeasurementDeviceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class MeasurementDeviceController extends BasicController<MeasurementDevice, Long, MeasurementDeviceRepository, MeasurementDeviceService> {

    public MeasurementDeviceController(MeasurementDeviceService repository) {
        super(repository);
    }

}
