package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.RangeSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.service.GrowStageTypeService;
import com.fg.grow_control.service.GrowingEventTypeService;
import com.fg.grow_control.service.GrowingParameterTypeService;
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

public class GrowCycleControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;
    @Autowired
    private GrowStageTypeService growStageTypeService;
    @Autowired
    private GrowingEventTypeService growingEventTypeService;

    @Test
    public void testCreateGrowCycle() {

        String stringForTestCreateGrowCycle = appendCurrentDateTime("StringForTestCreateGrowCycle");

        Optional<GrowStageType> existingStageType = growStageTypeService.findByName("StringForTestCreateGrowCycle");
        GrowStageType growStageType;
        if (existingStageType.isPresent()) {
            growStageType = existingStageType.get();
        } else {
            growStageType = GrowStageType.builder()
                    .name("StringForTestCreateGrowCycle")
                    .build();
        }

        Optional<GrowingEventType> existingEventType = growingEventTypeService.findByName("StringForTestCreateGrowCycle");
        GrowingEventType growingEventType;
        if (existingEventType.isPresent()) {
            growingEventType = existingEventType.get();
        } else {
            growingEventType = GrowingEventType.builder()
                    .name("StringForTestCreateGrowCycle")
                    .build();
        }

        //this is a new instance for each run test
        GrowingParameterType growingParameterType = GrowingParameterType.builder()
                .name(stringForTestCreateGrowCycle)
                .build();

        // TODO This might actually be a bug, we need to save this here, so two different objects do not try to create the same type with the same name.
        growingParameterType = growingParameterTypeService.createOrUpdate(growingParameterType);

        SimpleTimestamp simpleTimestampInit = SimpleTimestamp.builder()
                .day(24)
                .month(9)
                .year(2024)
                .hour(10)
                .minutes(30)
                .seconds(45)
                .build();

        SimpleTimestamp simpleTimestampFinal = SimpleTimestamp.builder()
                .day(28)
                .month(10)
                .year(2025)
                .hour(10)
                .minutes(30)
                .seconds(45)
                .build();

        EventSchedule eventSchedule = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(simpleTimestampFinal)
                .build();  // The rest of the attributes are not required for the FIXED type

        RangeSchedule rangeSchedule = RangeSchedule.builder()
                .type(ScheduleType.FIXED) // o ScheduleType.RELATIVE
                .start(simpleTimestampInit)
                .end(simpleTimestampFinal)
                .build();

        OptimalGrowingParameter optimalGrowingParameter = OptimalGrowingParameter.builder()
                .rangeSchedule(rangeSchedule)
                .optimalValue(5.0)
                    .build();

        MeasurementDevice measurementDevice = MeasurementDevice.builder()
                .growingParameterType(growingParameterType)
                .build();

       MeasuredGrowingParameter growingParameter = MeasuredGrowingParameter.builder()
               .measurementDevice(measurementDevice)
               .growingParameterType(growingParameterType)
               .optimalGrowingParameter(optimalGrowingParameter)
               .build();

       GrowingEvent growingEvent = GrowingEvent.builder()
               .growingEventType(growingEventType)
               .description(stringForTestCreateGrowCycle)
               .eventSchedule(eventSchedule)
               .build();

       GrowStage growStage = GrowStage.builder()
               .rangeSchedule(rangeSchedule)
               .growStageType(growStageType)
               .growingParameter(growingParameter)
               .growingEvent(growingEvent)
               .build();

       GrowCycle growCycleWithStage = GrowCycle.builder()
               .description(stringForTestCreateGrowCycle)
               .rangeSchedule(rangeSchedule)
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
