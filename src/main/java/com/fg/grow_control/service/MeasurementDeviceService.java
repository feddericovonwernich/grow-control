package com.fg.grow_control.service;

import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.MeasurementDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeasurementDeviceService extends BasicService<MeasurementDevice, Long, MeasurementDeviceRepository>{

    @Autowired
    public MeasurementDeviceService(MeasurementDeviceRepository repository) {
        super(repository);
    }

}
