package com.fg.grow_control.controller;

import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.DeviceTrigger;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.ActionDeviceRepository;
import com.fg.grow_control.service.ActionDeviceService;
import com.fg.grow_control.service.DeviceReadingService;
import com.fg.grow_control.service.DeviceTriggerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/actionDevice")
public class ActionDeviceController extends BasicController<ActionDevice, Long, ActionDeviceRepository, ActionDeviceService> {


    // Initialize logger for ActionDeviceController
    private static final Logger log = getLogger(ActionDeviceController.class);


    @Autowired
    private final DeviceReadingService deviceReadingService;

    @Autowired
    private final DeviceTriggerService deviceTriggerService;

    public ActionDeviceController(ActionDeviceService service, DeviceReadingService deviceReadingService,
                                  DeviceTriggerService deviceTriggerService) {
        super(service);
        this.deviceReadingService = deviceReadingService;
        this.deviceTriggerService = deviceTriggerService;
    }

    @GetMapping("/actionStatus/{id}")
    public ResponseEntity<Boolean> shouldTrigger(@PathVariable Long id) {
        try {
            ActionDevice actionDevice = service.getById(id);
            MeasurementDevice measurementDevice = actionDevice.getWatchedMeasurement();
            DeviceReading latestDeviceReading = deviceReadingService.getLastReadingForDevice(measurementDevice);
            DeviceTrigger latestDeviceTrigger = deviceTriggerService.getLastTriggerForDevice(actionDevice);
            // Check if it's triggering.
            if (!isTriggering(latestDeviceTrigger)) {
                // If it's not triggering, check if we should trigger.
                if (actionDevice.getActivationThreshold() < actionDevice.getDeactivationThreshold()) {
                    // Current needs to go up if triggered.
                    if (latestDeviceReading.getReading().longValue() < actionDevice.getActivationThreshold()) {
                        // Need to trigger
                        createTrigger(actionDevice, latestDeviceReading);
                        return ResponseEntity.ok(true);
                    }
                } else {
                    // Current needs to go down if triggered.
                    if (latestDeviceReading.getReading().longValue() > actionDevice.getActivationThreshold()) {
                        // Need to trigger
                        createTrigger(actionDevice, latestDeviceReading);
                        return ResponseEntity.ok(true);
                    }
                }
                // Keep not triggering.
                return ResponseEntity.ok(false);
            } else {
                // If it's triggering, check if we should stop.
                if (actionDevice.getActivationThreshold() < actionDevice.getDeactivationThreshold()) {
                    // Current needs to go up if triggered.
                    if (latestDeviceReading.getReading().longValue() > actionDevice.getDeactivationThreshold()) {
                        // Need to stop triggering
                        completeTrigger(latestDeviceTrigger);
                        return ResponseEntity.ok(false);
                    }
                } else {
                    // Current needs to go down if triggered.
                    if (latestDeviceReading.getReading().longValue() < actionDevice.getDeactivationThreshold()) {
                        // Need to stop triggering
                        completeTrigger(latestDeviceTrigger);
                        return ResponseEntity.ok(false);
                    }
                }
                // Keep triggering.
                return ResponseEntity.ok(true);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void completeTrigger(DeviceTrigger latestDeviceTrigger) {
        latestDeviceTrigger.setCompleted(true);
        deviceTriggerService.createOrUpdate(latestDeviceTrigger);
    }

    private void createTrigger(ActionDevice actionDevice, DeviceReading latestDeviceReading) {
        DeviceTrigger deviceTrigger = DeviceTrigger.builder()
                .triggeredDevice(actionDevice)
                .triggerTime(new Timestamp(System.currentTimeMillis()))
                .triggerValue(latestDeviceReading.getReading().longValue())
                .completed(false)
                .build();
        deviceTriggerService.createOrUpdate(deviceTrigger);
    }

    private boolean isTriggering(DeviceTrigger deviceTrigger) {
        if (deviceTrigger == null) {
            // It never triggered.
            return false;
        }
        return !deviceTrigger.isCompleted();
    }

}
