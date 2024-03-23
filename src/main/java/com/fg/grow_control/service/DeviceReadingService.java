package com.fg.grow_control.service;

import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.DeviceReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceReadingService extends BasicService<DeviceReading, Long, DeviceReadingRepository>{

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    public DeviceReadingService(DeviceReadingRepository repository) {
        super(repository);
    }

    public DeviceReading registerReading(DeviceReadingDTO deviceReadingDTO) {

        MeasurementDevice measurementDevice = measurementDeviceService.getById(deviceReadingDTO.getDeviceId());

        DeviceReading newReading = DeviceReading.builder()
                .measurementDevice(measurementDevice)
                .reading(deviceReadingDTO.getMeasurementValue())
                .build();

        return this.createOrUpdate(newReading);
    }

}
