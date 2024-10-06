package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.service.GrowStageService;
import com.fg.grow_control.service.GrowingParameterTypeService;
import com.fg.grow_control.service.MeasuredGrowingParameterService;
import com.fg.grow_control.service.MeasurementDeviceService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class DeviceReadingControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    private MeasuredGrowingParameterService measuredGrowingParameterService;

    @Autowired
    private GrowStageService growStageService;

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Test
    public void testRegisterReading() {

        MeasurementDevice measurementDevice = createOrGetMeasurementDeviceForType("StringForTestRegisterReading");

        // Make request
        DeviceReadingDTO deviceReadingDTO = DeviceReadingDTO.builder()
                                                .deviceId(measurementDevice.getId())
                                                .measurementValue(50.00)
                                                .build();

        String deviceReadingDTOJsonString = new Gson().toJson(deviceReadingDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(deviceReadingDTOJsonString, httpHeaders);

        String url = this.createURLWithPort("/readings/register");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
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
}
