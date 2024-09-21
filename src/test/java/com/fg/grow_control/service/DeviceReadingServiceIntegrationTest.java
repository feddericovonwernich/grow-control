package com.fg.grow_control.service;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.entity.MeasurementDevice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeviceReadingServiceIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private DeviceReadingService deviceReadingService;

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Test
    public void testDeviceReadingProcess() {
        Optional<GrowingParameterType> existingGrowingParameterType = growingParameterTypeService.findByName("StringForTestDeviceReadingProcess");
        GrowingParameterType growingParameterType;

        if (existingGrowingParameterType.isPresent()) {
            growingParameterType = existingGrowingParameterType.get();
        } else {
            growingParameterType = GrowingParameterType.builder()
                    .name("StringForTestDeviceReadingProcess")
                    .build();
            growingParameterType = growingParameterTypeService.createOrUpdate(growingParameterType);
        }

        // Verificar si ya existe el MeasurementDevice antes de crearlo
        Optional<MeasurementDevice> existingDevice = measurementDeviceService.findByGrowingParameterTypeName("StringForTestDeviceReadingProcess");
        MeasurementDevice device;

        if (existingDevice.isPresent()) {
            device = existingDevice.get();
        } else {
            device = MeasurementDevice.builder()
                    .growingParameterType(growingParameterType)
                    .build();
            device = measurementDeviceService.createOrUpdate(device);
        }

        Timestamp lastTimestamp = null;
        // Then, create many DeviceReading objects and save them
        // Simulating multiple readings for demonstration purposes
        for (int i = 0; i < 5; i++) {
            lastTimestamp = new Timestamp(System.currentTimeMillis());
            DeviceReading newReading = DeviceReading.builder()
                    .measurementDevice(device    )
                    .reading(Math.random() * 100) // Assuming random readings for simplicity
                    .timestamp(lastTimestamp)
                    .build();
            deviceReadingService.createOrUpdate(newReading);
        }

        // Fetch the latest reading for the given device and assert it is as expected
        DeviceReading latestReading = deviceReadingService.getLastReadingForDevice(device);

        assertNotNull(latestReading, "Latest reading should not be null");
        assertEquals(device.getId(), latestReading.getMeasurementDevice().getId(), "The latest reading should be for the saved device");
        assertEquals(lastTimestamp, latestReading.getTimestamp());
    }
}
