package com.fg.grow_control.controller;

import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.service.DeviceReadingService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/readings")
public class DeviceReadingController {

    private static final Logger log = LoggerFactory.getLogger(DeviceReadingController.class);

    @Autowired
    private DeviceReadingService deviceReadingService;


    @PostMapping("/register")
    public ResponseEntity<String> registerReading(@RequestBody DeviceReadingDTO deviceReadingDTO) {
        try {
            // Enhanced log message to include both device ID and measurement value for clearer debugging and tracking.
            log.info("Registering device reading: Device ID: {}, Measurement Value: {}",
                    deviceReadingDTO.getDeviceId(), deviceReadingDTO.getMeasurementValue());
            DeviceReading deviceReading = deviceReadingService.registerReading(deviceReadingDTO);
            return ResponseEntity.ok(String.format("Reading registered with ID: %d", deviceReading.getId()));
        } catch (EntityNotFoundException ex) {
            // Log when a device is not found during registration attempt
            log.error("Failed to register reading for device ID: {}. Device not found.",
                    deviceReadingDTO.getDeviceId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Could not find device with ID: %s to register the reading",
                            deviceReadingDTO.getDeviceId()));
        }
    }


}
