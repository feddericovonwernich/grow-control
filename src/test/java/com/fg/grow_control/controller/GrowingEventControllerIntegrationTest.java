package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.entity.schedule.Direction;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.OffsetReference;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.service.GrowingEventService;
import com.fg.grow_control.service.GrowingEventTypeService;
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
    @Autowired
    GrowingEventTypeService growingEventTypeService;

    @Test
    @Order(1)
    public void testCreateGrowingEvent() {

        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("StringForTestCreateGrowingEventTestType"))
                .build();

        EventSchedule eventSchedule = EventSchedule.builder()
                .date(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .type(ScheduleType.FIXED)
                .direction(null)
                .units(null)
                .unitValue(null)
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

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "The HTTP request failed. Response:" + response.getBody());

    }

    @Test
    @Order(2)
    public void updateEventScheduleThroughGrowingEvent_FixedType() {

        LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("updateEventScheduleThroughGrowingEvent_FixedType"))
                .build();

        growingEventType = growingEventTypeService.createOrUpdate(growingEventType);

        EventSchedule eventScheduleFixed = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(currentDateTime)))
                .direction(null)
                .units(null)
                .unitValue(null)
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("testDescription")
                .eventSchedule(eventScheduleFixed)
                .build();

        growingEvent = growingEventService.createOrUpdate(growingEvent);

        SimpleTimestamp newDate = SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(currentDateTime.plusDays(10)));

        growingEvent.getEventSchedule().setDate(newDate);
        growingEvent = growingEventService.createOrUpdate(growingEvent);

        GrowingEvent updatedGrowingEvent = growingEventService.getById(growingEvent.getId());

        Assertions.assertEquals(newDate, updatedGrowingEvent.getEventSchedule().getDate(), "The date was not updated correctly");
        Assertions.assertNull(updatedGrowingEvent.getEventSchedule().getDirection(), "The direction is not null");
        Assertions.assertNull(updatedGrowingEvent.getEventSchedule().getUnits(), "The units are not null");
        Assertions.assertNull(updatedGrowingEvent.getEventSchedule().getUnitValue(), "The unit value is not null");
    }

    @Test
    @Order(3)
    public void updateEventScheduleThroughGrowingEvent_RelativeType() {

        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("updateEventScheduleThroughGrowingEvent_RelativeType"))
                .build();

        growingEventType = growingEventTypeService.createOrUpdate(growingEventType);

        EventSchedule eventSchedule = EventSchedule.builder()
                .type(ScheduleType.RELATIVE)
                .direction(Direction.BEFORE)
                .units(ChronoUnit.WEEKS)
                .reference(OffsetReference.END)
                .unitValue(2.0)
                .date(null)  // Date must be null for RELATIVE
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("testDescription")
                .eventSchedule(eventSchedule)
                .build();

        growingEvent = growingEventService.createOrUpdate(growingEvent);

        EventSchedule scheduleToUpdate = growingEvent.getEventSchedule();
        scheduleToUpdate.setDirection(Direction.AFTER);
        scheduleToUpdate.setUnits(ChronoUnit.DAYS);
        scheduleToUpdate.setUnitValue(5.0);
        scheduleToUpdate.setDate(null);
        growingEvent.setEventSchedule(scheduleToUpdate);

        growingEventService.createOrUpdate(growingEvent);

        GrowingEvent updatedEvent = growingEventService.getById(growingEvent.getId());

        Assertions.assertEquals(Direction.AFTER, updatedEvent.getEventSchedule().getDirection(), "The direction was not updated correctly");
        Assertions.assertEquals(ChronoUnit.DAYS, updatedEvent.getEventSchedule().getUnits(), "The units were not updated correctly");
        Assertions.assertEquals(5.0, updatedEvent.getEventSchedule().getUnitValue(), "The unit value was not updated correctly");

        Assertions.assertNull(updatedEvent.getEventSchedule().getDate(), "The date should be null for a RELATIVE type");
    }

}