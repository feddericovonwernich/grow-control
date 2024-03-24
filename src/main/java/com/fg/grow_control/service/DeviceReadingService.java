package com.fg.grow_control.service;

import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.DeviceReadingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

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
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        return this.createOrUpdate(newReading);
    }


    public DeviceReading getLastReadingForDevice(MeasurementDevice measurementDevice) {
        // Utilizing custom repository method to find the last DeviceReading for a given MeasurementDevice
        return repository.findTopByMeasurementDeviceOrderByIdDesc(measurementDevice)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No readings found for device with ID: " + measurementDevice.getId()));
    }

}
