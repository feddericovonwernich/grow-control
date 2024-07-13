package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.entity.schedule.Direction;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.service.GrowingEventService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GrowingEventControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    GrowingEventService growingEventService;

    @Test
    @Order(1)
    public void testCreateGrowingEvent() {

        GrowingEventType growingEventType = GrowingEventType.builder()
                .name("TestType")
                .build();

        EventSchedule eventSchedule = EventSchedule.builder()
                .date(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .type(ScheduleType.FIXED)
                .direction(Direction.AFTER)
                .units(ChronoUnit.DAYS)
                .unitValue(2.0)
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("TestDescription")
                .eventSchedule(eventSchedule)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();

        String jsonString = gson.toJson(growingEvent);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);

        // Se crea la URL para la solicitud HTTP

        String url = this.createURLWithPort("/growingEvent");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert: Verificación de la respuesta HTTP exitosa

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud HTTP no fue exitosa. Respuesta: " + response.getBody());

    }

    @Test
    @Order(2)
    public void updateEventScheduleThrowGrowingEvent() {

        // Get the growing event with the schedule we want to change.
        GrowingEvent growingEvent = growingEventService.getById(1L);

        // Get the schedule.
        EventSchedule eventSchedule = growingEvent.getEventSchedule();

        // Set null these extra properties.
        eventSchedule.setDirection(null);
        eventSchedule.setUnits(null);
        eventSchedule.setUnitValue(null);

        // Save the event
        growingEventService.createOrUpdate(growingEvent);

        // Fetch it again just to make sure.
        GrowingEvent growingEvent1 = growingEventService.getById(1L);

        Assertions.assertNull(growingEvent1.getEventSchedule().getDirection());

    }
}
