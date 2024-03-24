package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.entity.GrowingParameterValueTime;
import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class GrowCycleControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Test
    public void testCreateGrowCycle() {

        // Se crean instancias de varios tipos de entidades que son necesarios para configurar un GrowCycle

        GrowStageType growStageType = GrowStageType.builder()
                .name("TestType")
                .build();

        GrowingEventType growingEventType = GrowingEventType.builder()
                .name("TestType")
                .build();

        GrowingParameterType growingParameterType = GrowingParameterType.builder()
                .name("TestType")
                .build();

        GrowingParameterValueTime growingParameterValueTime = GrowingParameterValueTime.builder()
                .value(1000L)
                .date(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build();

        OptimalGrowingParameter optimalGrowingParameter = OptimalGrowingParameter.builder()
                .date_start(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .date_end(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build();

        GrowingParameter growingParameter = GrowingParameter.builder()
                .growingParameterType(growingParameterType)
                .growingParameterValueTime(growingParameterValueTime)
                .optimalGrowingParameter(optimalGrowingParameter)
                .value(1000L)
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("TestDescription")
                .date(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build();

        GrowStage growStage = GrowStage.builder()
                .durationUnit("days")
                .durationValue(10L)
                .growStageType(growStageType)
                .growingParameter(growingParameter)
                .growingEvent(growingEvent)
                .build();

        GrowCycle growCycleWithStage = GrowCycle.builder()
                .description("Test description")
                .date_start(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .date_end(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .growStage(growStage)
                .build();

        // Make request: Creación de la solicitud HTTP para crear un GrowCycle

        // Se convierte el objeto growCycleWithStage a formato JSON. Se configuran los encabezados HTTP y se crea una entidad HTTP con el cuerpo JSON y los encabezados

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();

        String jsonString = gson.toJson(growCycleWithStage); // growCycleWithStage json string.

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);

        // Se crea la URL para la solicitud HTTP

        String url = this.createURLWithPort("/growCycle");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert: Verificación de la respuesta HTTP exitosa

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud HTTP no fue exitosa. Respuesta: " + response.getBody());
    }

}
