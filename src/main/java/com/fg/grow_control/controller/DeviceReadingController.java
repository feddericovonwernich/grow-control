package com.fg.grow_control.controller;

import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.service.DeviceReadingService;
import jakarta.persistence.EntityNotFoundException;
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

    @Autowired
    private DeviceReadingService deviceReadingService;

    @PostMapping("/register")
    public ResponseEntity<String> registerReading(@RequestBody DeviceReadingDTO deviceReadingDTO) {
        try {
            DeviceReading deviceReading = deviceReadingService.registerReading(deviceReadingDTO);
            return ResponseEntity.ok(String.format("Reading registered with ID: %d",
                    deviceReading.getId()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Could not find device with ID: %s to register the reading",
                            deviceReadingDTO.getDeviceId()));
        }
    }

}
