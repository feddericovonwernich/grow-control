package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.entity.MeasurementDevice;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class MeasurementDeviceControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Test
    public void testCreateOrUpdateMeasurementDevice() {

        // Set up a new MeasurementDevice instance for the test

        GrowingParameterType growingParameterType = GrowingParameterType.builder()
                .name("Temperature")
                .build();

        MeasurementDevice measurementDevice = MeasurementDevice.builder()
                .growingParameterType(growingParameterType)
                .build();

        // Convert the MeasurementDevice object to JSON using Gson
        String measurementDeviceJsonString = new Gson().toJson(measurementDevice);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        // Wrap the MeasurementDevice JSON string and headers in an HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(measurementDeviceJsonString, httpHeaders);

        // Construct the URL for the createOrUpdate endpoint
        String url = this.createURLWithPort("/device");

        // Execute the POST request to the createOrUpdate endpoint
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert the status code to ensure it is in the 2XX success range
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}
