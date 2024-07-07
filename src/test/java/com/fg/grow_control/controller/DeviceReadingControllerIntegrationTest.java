package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.service.MeasurementDeviceService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class DeviceReadingControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Test
    public void testRegisterReading() {

        // Set up

        GrowingParameterType growingParameterType
                = GrowingParameterType.builder()
                .name("Humedad")
                .build();

        MeasurementDevice measurementDevice
                = MeasurementDevice.builder()
                .growingParameterType(growingParameterType)
                .build();

        measurementDevice = measurementDeviceService.createOrUpdate(measurementDevice);

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

}
