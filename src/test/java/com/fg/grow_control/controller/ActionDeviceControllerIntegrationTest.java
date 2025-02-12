package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ActionDeviceControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    GrowStageService growStageService;

    @Autowired
    private ActionDeviceService actionDeviceService;

    @Autowired
    private DeviceReadingService deviceReadingService;

    @Autowired
    GrowStageTypeService growStageTypeService;

    @Autowired
    private ActionDeviceController actionDeviceController;

    @Autowired
    MeasuredGrowingParameterService measuredGrowingParameterService;

    @Test
    public void testCreateActionDevice() {

        //all measurement devices will be different to ensure the creation of a new action device everytime
        MeasurementDevice measurementDevice = createMeasurementDevice(appendCurrentDateTime("StringForTestCreateActionDevice"));

        ActionDevice actionDevice = ActionDevice.builder()
                .activationThreshold(50)
                .deactivationThreshold(20)
                .watchedMeasurement(measurementDevice)
                .build();

        // Convert the ActionDevice object to a JSON string
        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(actionDevice);

        // Set up HTTP headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        // Create an HTTP entity with the JSON body and headers
        HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);

        // Construct the URL for the POST request
        String url = this.createURLWithPort("/actionDevice");

        // Make the POST request and capture the response
        ResponseEntity<ActionDevice> response = restTemplate.exchange(url, HttpMethod.POST, entity, ActionDevice.class);

        // Validate the response to ensure it's successful
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(),
                "HTTP request to create ActionDevice was not successful. Response: " + response.getBody());

        // Test modifying

        // Modify the ActionDevice properties
        actionDevice = response.getBody();
        actionDevice.setActivationThreshold(60); // Updated activation threshold
        actionDevice.setDeactivationThreshold(40); // Updated deactivation threshold

        // Convert the updated ActionDevice object to a JSON string for request body
        jsonString = gson.toJson(actionDevice);

        // Set up HTTP headers
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        // Create an HTTP entity with the updated JSON body and headers
        entity = new HttpEntity<>(jsonString, httpHeaders);

        // Construct the URL for the PUT request including the ID of the ActionDevice to update
        url = this.createURLWithPort("/actionDevice");

        // Make the PUT request and capture the response
        ResponseEntity<ActionDevice> responseUpdate = restTemplate.exchange(url, HttpMethod.POST, entity, ActionDevice.class);
        System.out.println("Response Body as String: " + responseUpdate.getBody());

        // Validate the response to ensure it's successful
        Assertions.assertTrue(responseUpdate.getStatusCode().is2xxSuccessful(),
                "HTTP request to update ActionDevice was not successful. Response: " + response.getBody());

        // Assert that the updated values of activation and deactivation thresholds are properly reflected in the response
        Assertions.assertEquals(60, responseUpdate.getBody().getActivationThreshold(),
                "The activation threshold in the response did not match the expected updated value.");

        Assertions.assertEquals(40, responseUpdate.getBody().getDeactivationThreshold(),
                "The deactivation threshold in the response did not match the expected updated value.");

    }



    private MeasurementDevice createMeasurementDevice(String typeName) {

        GrowingParameterType growingParameterType = GrowingParameterType.builder()
                .name(typeName)
                .build();

       // Create and persist the GrowStage instance
        GrowStageType growStageType = GrowStageType.builder()
                .name(typeName)
                .build();

        GrowStage growStage = GrowStage.builder()
                .growStageType(growStageType)
                .build();

        // Persist the GrowStage before using it
        growStage = growStageService.createOrUpdate(growStage);

        // Create and persist the MeasuredGrowingParameter instance before setting it in MeasurementDevice
        MeasuredGrowingParameter measuredGrowingParameter = MeasuredGrowingParameter.builder()
                .growStage(growStage) // Now growStage is a persisted entity
                .growingParameterType(growingParameterType)
                .build();

        // Persist MeasuredGrowingParameter before using it in MeasurementDevice
        measuredGrowingParameter = measuredGrowingParameterService.createOrUpdate(measuredGrowingParameter); // Assuming you have a service for this

        // Now create and save the MeasurementDevice instance
        MeasurementDevice measurementDevice = MeasurementDevice.builder()
                .growingParameterType(growingParameterType)
                .growingParameter(measuredGrowingParameter) // Now it's a persisted entity
                .build();

        measurementDevice = measurementDeviceService.createOrUpdate(measurementDevice);

        return measurementDevice;
    }

    private MeasurementDevice createOrGetMeasurementDeviceForType(String typeName) {

        Optional<MeasurementDevice> existingDevice = measurementDeviceService.findByGrowingParameterTypeName(typeName);

        if (existingDevice.isPresent()) {
            return existingDevice.get();
        }

        return createMeasurementDevice(typeName);
    }

        @Test
    public void testShouldTrigger_DriveValueUp_StopsWhenReached() {

        //here we can use the method called "create or get" because in this test we are evaluating other question.
        MeasurementDevice measurementDevice = createOrGetMeasurementDeviceForType("StringForTestDriveValueUp");

        Optional<ActionDevice> existingActionDevice = actionDeviceService.getActionDeviceByMeasurementDevice(measurementDevice);
        ActionDevice actionDevice;
        if (existingActionDevice.isPresent()) {
            actionDevice = existingActionDevice.get();
        } else {
            actionDevice = ActionDevice.builder()
                    .activationThreshold(20)
                    .deactivationThreshold(30)
                    .watchedMeasurement(measurementDevice)
                    .build();
            actionDevice = actionDeviceService.createOrUpdate(actionDevice);
        }


        DeviceReadingDTO preReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(21.0)
                .deviceId(measurementDevice.getId())
                .build(); // Above activation, should not trigger

        DeviceReadingDTO initialReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(19.0)
                .deviceId(measurementDevice.getId())
                .build(); // Below activation, should trigger

        DeviceReadingDTO middleReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(25.0)
                .deviceId(measurementDevice.getId())
                .build(); // Should keep triggering.

        DeviceReadingDTO finalReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(31.0)
                .deviceId(measurementDevice.getId())
                .build(); // Above deactivation, should stop

        // Simulate should not trigger
        deviceReadingService.registerReading(preReadingDTO);
        ResponseEntity<Boolean> responsePre = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should trigger as initial reading is below activation threshold
        Assertions.assertEquals(Boolean.FALSE, responsePre.getBody(), "Expected to not trigger as the reading is above the activation threshold.");

        // Simulate action device triggering
        deviceReadingService.registerReading(initialReadingDTO);
        ResponseEntity<Boolean> responseInitial = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should trigger as initial reading is below activation threshold
        Assertions.assertEquals(Boolean.TRUE, responseInitial.getBody(), "Expected to trigger as the reading is below the activation threshold.");

        // Simulate action device should keep triggering
        deviceReadingService.registerReading(middleReadingDTO);
        ResponseEntity<Boolean> responseMiddle = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should trigger as initial reading is below activation threshold
        Assertions.assertEquals(Boolean.TRUE, responseMiddle.getBody(), "Expected to keep trigger as the reading is below the deactivation threshold.");

        // Simulate device reading crossing the deactivation threshold
        deviceReadingService.registerReading(finalReadingDTO);
        ResponseEntity<Boolean> responseFinal = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should stop triggering as final reading is above deactivation threshold
        Assertions.assertEquals(Boolean.FALSE, responseFinal.getBody(), "Expected to stop triggering as the reading is above the deactivation threshold.");
    }

    @Test
    public void testShouldTrigger_DriveValueDown_StopsWhenReached() {

        //here we can use the method called "create or get" because in this test we are evaluating other question.
        MeasurementDevice measurementDevice = createOrGetMeasurementDeviceForType("StringForTestDriveValueDown");

        Optional<ActionDevice> existingActionDevice = actionDeviceService.getActionDeviceByMeasurementDevice(measurementDevice);
        ActionDevice actionDevice;
        if (existingActionDevice.isPresent()) {
            actionDevice = existingActionDevice.get();
        } else {
            actionDevice = ActionDevice.builder()
                    .activationThreshold(20)
                    .deactivationThreshold(10)
                    .watchedMeasurement(measurementDevice)
                    .build();
            actionDevice = actionDeviceService.createOrUpdate(actionDevice);
        }

        DeviceReadingDTO preReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(19.0)
                .deviceId(measurementDevice.getId())
                .build(); // Above activation, should not trigger

        DeviceReadingDTO initialReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(21.0)
                .deviceId(measurementDevice.getId())
                .build(); // Above activation, should trigger

        DeviceReadingDTO middleReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(15.0)
                .deviceId(measurementDevice.getId())
                .build(); // Should keep triggering.

        DeviceReadingDTO finalReadingDTO = DeviceReadingDTO.builder()
                .measurementValue(09.0)
                .deviceId(measurementDevice.getId())
                .build(); // Below deactivation, should stop

        // Simulate should not trigger
        deviceReadingService.registerReading(preReadingDTO);
        ResponseEntity<Boolean> responsePre = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should trigger as initial reading is below activation threshold
        Assertions.assertEquals(Boolean.FALSE, responsePre.getBody(), "Expected to not trigger as the reading is below the activation threshold.");

        // Simulate action device triggering
        deviceReadingService.registerReading(initialReadingDTO);
        ResponseEntity<Boolean> responseInitial = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should trigger as initial reading is above activation threshold
        Assertions.assertEquals(Boolean.TRUE, responseInitial.getBody(), "Expected to trigger as the reading is above the activation threshold.");

        // Simulate action device should keep triggering
        deviceReadingService.registerReading(middleReadingDTO);
        ResponseEntity<Boolean> responseMiddle = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should trigger as initial reading is below activation threshold
        Assertions.assertEquals(Boolean.TRUE, responseMiddle.getBody(), "Expected to keep trigger as the reading is above the deactivation threshold.");

        // Simulate device reading dropping below the deactivation threshold
        deviceReadingService.registerReading(finalReadingDTO);
        ResponseEntity<Boolean> responseFinal = actionDeviceController.shouldTrigger(actionDevice.getId());
        // Assert that we should stop triggering as final reading is below deactivation threshold
        Assertions.assertEquals(Boolean.FALSE, responseFinal.getBody(), "Expected to stop triggering as the reading is below the deactivation threshold.");
    }
}

