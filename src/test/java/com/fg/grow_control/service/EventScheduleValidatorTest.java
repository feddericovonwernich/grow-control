package com.fg.grow_control.service;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.entity.SimpleTimestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class EventScheduleValidatorTest extends BasicApplicationintegrationTest {

    @Autowired
    EventScheduleService eventScheduleService;

    @Test
    public void testCreateOrUpdate_ValidFixedSchedule() {
        // create a valid EventSchedule for fixed type
        EventSchedule fixedSchedule = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(SimpleTimestamp.builder()
                        .day(15)
                        .month(9)
                        .year(2024)
                        .hour(12)
                        .minutes(30)
                        .seconds(0)
                        .build())  // El campo date es requerido para FIXED
                .units(null)  // Otros campos deben ser nulos
                .unitValue(null)
                .direction(null)
                .reference(null)
                .build();

        // Execute and verify that its not throws exceptions
        EventSchedule result = eventScheduleService.createOrUpdate(fixedSchedule);

        Assertions.assertNotNull(result);
    }

    @Test
    public void testCreateOrUpdate_InvalidFixedSchedule() {
        // Create a invalid EventSchedule for a fixed type
        EventSchedule fixedSchedule = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(SimpleTimestamp.builder()
                        .day(15)
                        .month(9)
                        .year(2024)
                        .hour(12)
                        .minutes(30)
                        .seconds(0)
                        .build())  // El campo date es requerido
                .units(null)  // Todos los otros deben ser nulos
                .unitValue(5.0) // Esto es incorrecto para FIXED
                .direction(null)
                .reference(null)
                .build();

        // verify that its throws a exception
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            eventScheduleService.createOrUpdate(fixedSchedule);
        });

        // verify the error message
        String expectedMessage = "Unit value must be null when schedule type is FIXED.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateOrUpdate_InvalidRelativeSchedule() {
        // create a invalid eventschedule for relative schedule
        EventSchedule relativeSchedule = EventSchedule.builder()
                .type(ScheduleType.RELATIVE)
                .date(SimpleTimestamp.builder() // this is invalid
                        .day(15)
                        .month(9)
                        .year(2024)
                        .hour(12)
                        .minutes(30)
                        .seconds(0)
                        .build())
                .units(null)  // units must be not null
                .unitValue(5.0)
                .direction(null)
                .reference(null)
                .build();

        // Verificar que se lance una excepción de validación
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            eventScheduleService.createOrUpdate(relativeSchedule);
        });

        // Verificar el mensaje de error esperado
        String expectedMessage = "Date must be null when schedule type is RELATIVE.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}