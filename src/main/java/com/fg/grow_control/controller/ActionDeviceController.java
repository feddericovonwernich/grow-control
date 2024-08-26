package com.fg.grow_control.controller;

import com.fg.grow_control.entity.*;
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

import static com.fg.grow_control.entity.SimpleTimestamp.fromSqlTimestamp;
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
            log.debug("Getting action device by ID: {}", id);
            ActionDevice actionDevice = service.getById(id);
            MeasurementDevice measurementDevice = actionDevice.getWatchedMeasurement();
            DeviceReading latestDeviceReading = deviceReadingService.getLastReadingForDevice(measurementDevice);
            DeviceTrigger latestDeviceTrigger = deviceTriggerService.getLastTriggerForDevice(actionDevice);
            log.info("Evaluating triggering condition for device ID: {}", id);
            // Check if it's triggering.
            if (!isTriggering(latestDeviceTrigger)) {
                log.debug("Device is not currently triggering.");
                // If it's not triggering, check if we should trigger.
                if (actionDevice.getActivationThreshold() < actionDevice.getDeactivationThreshold()) {
                    log.debug("Activation condition based on threshold comparison.");
                    // Current needs to go up to be triggered.
                    if (latestDeviceReading.getReading().longValue() < actionDevice.getActivationThreshold()) {
                        log.info("Triggering device due to reading lower than activation threshold.");
                        // Need to trigger
                        createTrigger(actionDevice, latestDeviceReading);
                        return ResponseEntity.ok(true);
                    }
                } else {
                    log.debug("Deactivation condition based on threshold comparison.");
                    // Current needs to go down to be triggered.
                    if (latestDeviceReading.getReading().longValue() > actionDevice.getActivationThreshold()) {
                        log.info("Triggering device due to reading higher than activation threshold.");
                        // Need to trigger
                        createTrigger(actionDevice, latestDeviceReading);
                        return ResponseEntity.ok(true);
                    }
                }
                log.info("No triggering condition met for device ID: {}", id);
                // Keep not triggering.
                return ResponseEntity.ok(false);
            } else {
                log.debug("Device is currently triggering. Evaluating stop condition.");
                // If it's triggering, check if we should stop.
                if (actionDevice.getActivationThreshold() < actionDevice.getDeactivationThreshold()) {
                    log.debug("Stop condition check based on threshold comparison.");
                    // Current needs to go up to stop triggering.
                    if (latestDeviceReading.getReading().longValue() > actionDevice.getDeactivationThreshold()) {
                        log.info("Stopping device trigger due to reading higher than deactivation threshold.");
                        // Need to stop triggering
                        completeTrigger(latestDeviceTrigger);
                        return ResponseEntity.ok(false);
                    }
                } else {
                    log.debug("Deactivation condition check for currently triggering device.");
                    // Current needs to go down to stop triggering.
                    if (latestDeviceReading.getReading().longValue() < actionDevice.getDeactivationThreshold()) {
                        log.info("Stopping device trigger due to reading lower than deactivation threshold.");
                        // Need to stop triggering
                        completeTrigger(latestDeviceTrigger);
                        return ResponseEntity.ok(false);
                    }
                }
                log.info("Continuing trigger for device ID: {}", id);
                // Keep triggering.
                return ResponseEntity.ok(true);
            }
        } catch (EntityNotFoundException e) {
            log.error("Device not found for ID: {}", id, e);
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
                .triggerTime(fromSqlTimestamp(new Timestamp(System.currentTimeMillis())))
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
