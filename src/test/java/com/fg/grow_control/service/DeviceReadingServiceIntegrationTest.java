package com.fg.grow_control.service;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.DeviceReadingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeviceReadingServiceIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    DeviceReadingService deviceReadingService;
    @Autowired
    MeasurementDeviceService measurementDeviceService;

    @Test
    public void testDeviceReadingProcess() {
        GrowingParameterType growingParameterType = GrowingParameterType.builder()
                .name("Temperature")
                .build();

        // First, create a measurement device and save it
        MeasurementDevice newDevice = MeasurementDevice.builder()
                .growingParameterType(growingParameterType) // Assuming someGrowingParameterType is predefined or fetched from somewhere
                .build();

        MeasurementDevice savedDevice = measurementDeviceService.createOrUpdate(newDevice);

        Timestamp lastTimestamp = null;
        // Then, create many DeviceReading objects and save them
        // Simulating multiple readings for demonstration purposes
        for (int i = 0; i < 5; i++) {
            lastTimestamp = new Timestamp(System.currentTimeMillis());
            DeviceReading newReading = DeviceReading.builder()
                    .measurementDevice(savedDevice)
                    .reading(Math.random() * 100) // Assuming random readings for simplicity
                    .timestamp(lastTimestamp)
                    .build();
            deviceReadingService.createOrUpdate(newReading);
        }

        // Fetch the latest reading for the given device and assert it is as expected
        DeviceReading latestReading = deviceReadingService.getLastReadingForDevice(savedDevice);

        assertNotNull(latestReading, "Latest reading should not be null");
        assertEquals(savedDevice.getId(), latestReading.getMeasurementDevice().getId(), "The latest reading should be for the saved device");
        assertEquals(lastTimestamp, latestReading.getTimestamp());
    }
}
