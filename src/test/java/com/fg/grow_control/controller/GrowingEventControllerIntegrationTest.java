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

        // Se crea la URL para la solicitud HTTP

        String url = this.createURLWithPort("/growingEvent");

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Assert: Verificación de la respuesta HTTP exitosa

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful(), "La solicitud HTTP no fue exitosa. Respuesta: " + response.getBody());

    }

    @Test
    @Order(2)
    public void updateEventScheduleThroughGrowingEvent_FixedType() {

        LocalDateTime currentDateTime = LocalDateTime.now();

        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("updateEventScheduleThroughGrowingEvent_FixedType"))
                .build();

        growingEventType = growingEventTypeService.createOrUpdate(growingEventType);

        EventSchedule eventScheduleFixed = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(LocalDateTime.now())))
                .direction(null)
                .units(null)
                .unitValue(null)
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("testDescription")
                .eventSchedule(eventScheduleFixed)
                .build();

        growingEvent= growingEventService.createOrUpdate(growingEvent);

        // Obtener el EventSchedule del evento y modificarlo
        EventSchedule scheduleToUpdate = growingEvent.getEventSchedule();
        SimpleTimestamp newDate = SimpleTimestamp.fromSqlTimestamp(java.sql.Timestamp.valueOf(currentDateTime.plusDays(10)));
        scheduleToUpdate.setDate(newDate);
        growingEvent.setEventSchedule(scheduleToUpdate);

        // Guardar el evento con los cambios
        growingEvent=growingEventService.createOrUpdate(growingEvent);

        // Verificar que los cambios se guardaron correctamente
        GrowingEvent updatedGrowingEvent = growingEventService.getById(growingEvent.getId());

        // Comparar campos individuales sino falla
        Assertions.assertEquals(newDate.getDay(), updatedGrowingEvent.getEventSchedule().getDate().getDay(), "El día no fue actualizado correctamente");
        Assertions.assertEquals(newDate.getMonth(), updatedGrowingEvent.getEventSchedule().getDate().getMonth(), "El mes no fue actualizado correctamente");
        Assertions.assertEquals(newDate.getYear(), updatedGrowingEvent.getEventSchedule().getDate().getYear(), "El año no fue actualizado correctamente");
        Assertions.assertEquals(newDate.getHour(), updatedGrowingEvent.getEventSchedule().getDate().getHour(), "La hora no fue actualizada correctamente");
        Assertions.assertEquals(newDate.getMinutes(), updatedGrowingEvent.getEventSchedule().getDate().getMinutes(), "Los minutos no fueron actualizados correctamente");
        Assertions.assertEquals(newDate.getSeconds(), updatedGrowingEvent.getEventSchedule().getDate().getSeconds(), "Los segundos no fueron actualizados correctamente");
        Assertions.assertNull(updatedGrowingEvent.getEventSchedule().getDirection(), "La dirección no es null");
        Assertions.assertNull(updatedGrowingEvent.getEventSchedule().getUnits(), "Las unidades no son null");
        Assertions.assertNull(updatedGrowingEvent.getEventSchedule().getUnitValue(), "El valor de la unidad no es null");
    }

    @Test
    @Order(3)
    public void updateEventScheduleThroughGrowingEvent_RelativeType() {

        // Crear un GrowingEventType para el test
        GrowingEventType growingEventType = GrowingEventType.builder()
                .name(appendCurrentDateTime("updateEventScheduleThroughGrowingEvent_RelativeType"))
                .build();

        growingEventType = growingEventTypeService.createOrUpdate(growingEventType);

        // Crear un EventSchedule con tipo RELATIVE
        EventSchedule eventSchedule = EventSchedule.builder()
                .type(ScheduleType.RELATIVE)
                .direction(Direction.BEFORE)  // Valores relevantes para RELATIVE
                .units(ChronoUnit.WEEKS)
                .reference(OffsetReference.END)
                .unitValue(2.0)
                .date(null)  // Date debe ser null para RELATIVE
                .build();

        GrowingEvent growingEvent = GrowingEvent.builder()
                .growingEventType(growingEventType)
                .description("testDescription")
                .eventSchedule(eventSchedule)
                .build();

        growingEvent = growingEventService.createOrUpdate(growingEvent);

        // Obtener el EventSchedule del evento y modificarlo
        EventSchedule scheduleToUpdate = growingEvent.getEventSchedule();
        scheduleToUpdate.setDirection(Direction.AFTER);  // Cambiar dirección
        scheduleToUpdate.setUnits(ChronoUnit.DAYS);      // Cambiar unidades
        scheduleToUpdate.setUnitValue(5.0);              // Cambiar valor de la unidad
        scheduleToUpdate.setDate(null);                  // Asegurarse de que date sigue siendo null para RELATIVE
        growingEvent.setEventSchedule(scheduleToUpdate);

        // Guardar el evento con los cambios
        growingEventService.createOrUpdate(growingEvent);

        // Verificar que los cambios se guardaron correctamente
        GrowingEvent updatedEvent = growingEventService.getById(growingEvent.getId());

        // Aserciones específicas para RELATIVE: verificar direction, units y unitValue
        Assertions.assertEquals(Direction.AFTER, updatedEvent.getEventSchedule().getDirection(), "La dirección no fue actualizada correctamente");
        Assertions.assertEquals(ChronoUnit.DAYS, updatedEvent.getEventSchedule().getUnits(), "Las unidades no fueron actualizadas correctamente");
        Assertions.assertEquals(5.0, updatedEvent.getEventSchedule().getUnitValue(), "El valor de la unidad no fue actualizado correctamente");

        // Verificar que la fecha sigue siendo null para un tipo RELATIVE
        Assertions.assertNull(updatedEvent.getEventSchedule().getDate(), "La fecha debería ser null para un tipo RELATIVE");
    }

}