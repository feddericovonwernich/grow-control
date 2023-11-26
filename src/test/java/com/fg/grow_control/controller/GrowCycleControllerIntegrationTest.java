package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.service.GrowCycleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class GrowCycleControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private GrowCycleService growCycleService;

    @Test
    public void testCreateGrowCycle() {

        // Set up
        GrowStage growStage = GrowStage.builder()
                .durationUnit("days")
                .durationValue(10L)
                .build();

        String description = "Test description";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateStart = LocalDateTime.parse("2023-12-06T11:53:27.000", formatter);
        LocalDateTime dateEnd = LocalDateTime.parse("2023-12-07T11:53:27.000", formatter);


        // Este objeto, cuando se guarda, también tiene que persistir el grow stage.
        GrowCycle growCycleWithStage = GrowCycle.builder()
                .description(description)
                .date_start(java.sql.Timestamp.valueOf(dateStart))
                .date_end(java.sql.Timestamp.valueOf(dateEnd))
                .growStages(Collections.singletonList(growStage))
                .build();

        // Este objeto, no tiene un grow stage, por lo tanto no hay que persistir nada ni crear uno.
        //GrowCycle growCycleWithoutStage = GrowCycle.builder()
        //        .description("Test description")
        //        .build();

        // Make request

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();
        String jsonString = gson.toJson(growCycleWithStage); // growCycleWithStage json string.

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);

        String url = this.createURLWithPort("/growCycle");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud HTTP no fue exitosa. Respuesta: " + response.getBody());

    }

}
