package com.fg.grow_control.controller;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.*;
import com.fg.grow_control.entity.schedule.Direction;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.service.EventScheduleService;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GrowingEventControllerIntegrationTest extends BasicApplicationintegrationTest {

    @Autowired
    GrowingEventService growingEventService;

    @Autowired
    private EventScheduleService eventScheduleService;

    @Autowired
    private GrowingEventTypeService growingEventTypeService;

    @Test
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

        // Se crea la URL para la solicitud HTTP

        String url = this.createURLWithPort("/growingEvent");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert: Verificación de la respuesta HTTP exitosa

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud HTTP no fue exitosa. Respuesta: " + response.getBody());

    }

    @Test
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

        // Obtener el EventSchedule del evento y modificarlo
        EventSchedule scheduleToUpdate = growingEvent.getEventSchedule();
        scheduleToUpdate.setDirection(null);
        scheduleToUpdate.setUnits(null);
        scheduleToUpdate.setUnitValue(null);

        // Guardar el evento con los cambios
        growingEventService.createOrUpdate(growingEvent);

        // Verificar que los cambios se guardaron correctamente
        GrowingEvent updatedEvent = growingEventService.getById(growingEvent.getId());
        Assertions.assertNull(updatedEvent.getEventSchedule().getDirection(), "La dirección no fue actualizada a null correctamente");
        Assertions.assertNull(updatedEvent.getEventSchedule().getUnits(), "Las unidades no fueron actualizadas a null correctamente");
        Assertions.assertNull(updatedEvent.getEventSchedule().getUnitValue(), "El valor de la unidad no fue actualizado a null correctamente");
    }
}