package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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

        // TODO Fill these with schedules.

        OptimalGrowingParameter optimalGrowingParameter = OptimalGrowingParameter.builder()
//                .date_start(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
//                .date_end(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .build();

        MeasurementDevice measurementDevice= MeasurementDevice.builder()
                .growingParameterType(growingParameterType)
                .build();

        MeasuredGrowingParameter growingParameter = MeasuredGrowingParameter.builder()
                .measurementDevice(measurementDevice)
                .growingParameterType(growingParameterType)
                .optimalGrowingParameter(optimalGrowingParameter)
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("TestDescription")
//                .date(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .build();

        GrowStage growStage = GrowStage.builder()
//                .durationUnit("days")
//                .durationValue(10L)
                .growStageType(growStageType)
                .growingParameter(growingParameter)
                .growingEvent(growingEvent)
                .build();

        GrowCycle growCycleWithStage = GrowCycle.builder()
                .description("Test description")
//                .date_start(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
//                .date_end(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
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
