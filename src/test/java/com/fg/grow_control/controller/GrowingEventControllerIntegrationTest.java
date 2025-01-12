package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.entity.schedule.Direction;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.repository.GrowingEventRepository;
import com.fg.grow_control.service.EventScheduleService;
import com.fg.grow_control.service.GrowingEventService;
import com.fg.grow_control.service.GrowingEventTypeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.transaction.Transactional;
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

    @Autowired
    private EventScheduleService eventScheduleService;

    @Autowired
    private GrowingEventTypeService growingEventTypeService;

    @Autowired
    private GrowingEventRepository growingEventRepository;

    @Test
    @Transactional
    @Order(1)
    public void testCreateGrowingEvent() {
        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("StringForTestCreateGrowingEventTestType"))
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
                .description("testDescription")
                .eventSchedule(eventSchedule)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();

        String jsonString = gson.toJson(growingEvent);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);

        String url = this.createURLWithPort("/growingEvent");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud HTTP no fue exitosa. Respuesta: " + response.getBody());

    }

    @Test
    @Transactional
    @Order(2)
    public void updateEventScheduleThroughGrowingEvent() {
        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("updateEventScheduleThroughGrowingEvent"))
                .build();

        growingEventType = growingEventTypeService.createOrUpdate(growingEventType);

        EventSchedule eventSchedule = EventSchedule.builder()
                .date(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .type(ScheduleType.FIXED)
                .direction(Direction.AFTER)
                .units(ChronoUnit.DAYS)
                .unitValue(2.0)
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("testDescription")
                .eventSchedule(eventSchedule)
                .build();

        growingEvent = growingEventService.createOrUpdate(growingEvent);

        EventSchedule scheduleToUpdate = growingEvent.getEventSchedule();
        scheduleToUpdate.setDirection(null);
        scheduleToUpdate.setUnits(null);
        scheduleToUpdate.setUnitValue(null);

        growingEventService.createOrUpdate(growingEvent);

        GrowingEvent updatedEvent = growingEventService.getById(growingEvent.getId());
        Assertions.assertNull(updatedEvent.getEventSchedule().getDirection(), "La dirección no fue actualizada a null correctamente");
        Assertions.assertNull(updatedEvent.getEventSchedule().getUnits(), "Las unidades no fueron actualizadas a null correctamente");
        Assertions.assertNull(updatedEvent.getEventSchedule().getUnitValue(), "El valor de la unidad no fue actualizado a null correctamente");
    }

    @Test
    @Transactional
    @Order(3)
    public void testGetAllGrowingEvents() {

        long count = growingEventRepository.count();
        double newObjects = 10 - count;
        // Create 3 objects
        for (int i = 0; i < newObjects + 1; i++) {
            GrowingEventType growingEventType = GrowingEventType.builder()
                    .name(appendCurrentDateTime("String-TestCreateGrowingEventTestType" + i))
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
                    .description("testDescription_" + i)
                    .eventSchedule(eventSchedule)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    .create();
            String jsonString = gson.toJson(growingEvent);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(jsonString, httpHeaders);

            String url = this.createURLWithPort("/growingEvent");

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(),
                    "The HTTP request was not successful for event " + i + ". Response: " + response.getBody());
        }

        int page = 0;
        int size = 10;

        //First Page
        String urlGetPage1 = this.createURLWithPort("/growingEvent?page=" + page + "&size=" + size);

        ResponseEntity<String> getAllResponsePage1 = restTemplate.exchange(urlGetPage1, HttpMethod.GET, null, String.class);

        Assertions.assertTrue(getAllResponsePage1.getStatusCode().is2xxSuccessful(),
                "The GET request for page 1 was not successful. Response: " + getAllResponsePage1.getBody());

        // Deserealize body
        JsonObject responseBodyPage1 = JsonParser.parseString(getAllResponsePage1.getBody()).getAsJsonObject();
        int elementsInPage1 = responseBodyPage1.get("content").getAsJsonArray().size();

        Assertions.assertEquals(10, elementsInPage1, "The first page should contain 10 elements.");

        //Second Page
        page = 1;
        String urlGetPage2 = this.createURLWithPort("/growingEvent?page=" + page + "&size=" + size);

        ResponseEntity<String> getAllResponsePage2 = restTemplate.exchange(urlGetPage2, HttpMethod.GET, null, String.class);

        Assertions.assertTrue(getAllResponsePage2.getStatusCode().is2xxSuccessful(),
                "The GET request for page 2 was not successful. Response: " + getAllResponsePage2.getBody());

        //Deserealize body
        JsonObject responseBodyPage2 = JsonParser.parseString(getAllResponsePage2.getBody()).getAsJsonObject();
        int elementsInPage2 = responseBodyPage2.get("content").getAsJsonArray().size();

        Assertions.assertEquals(1, elementsInPage2, "The second page should contain 1 element.");

        //Verify pagination properties
        int totalPages = responseBodyPage1.get("totalPages").getAsInt();
        int totalElements = responseBodyPage1.get("totalElements").getAsInt();

        Assertions.assertEquals(2, totalPages, "Total pages should be 2.");
        Assertions.assertEquals(11, totalElements, "Total elements should be 11.");
        Assertions.assertEquals(size, responseBodyPage1.get("size").getAsInt(), "Page size should match the expected size.");
    }
}