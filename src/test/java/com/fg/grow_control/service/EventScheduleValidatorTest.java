package com.fg.grow_control.service;

import com.fg.grow_control.BasicApplicationintegrationTest;
import com.fg.grow_control.entity.MessagePropertiesKeys;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.entity.SimpleTimestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static com.fg.grow_control.entity.MessagePropertiesKeys.ERROR_DATE_RELATIVE_SCHEDULE_MUST_BE_NULL;

public class EventScheduleValidatorTest extends BasicApplicationintegrationTest {

    @Autowired
    EventScheduleService eventScheduleService;

    @Autowired
    private MessageSource messageSource;

    @Test
    public void testCreateOrUpdate_ValidFixedSchedule() {
        EventSchedule fixedSchedule = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(SimpleTimestamp.builder()
                        .day(15)
                        .month(9)
                        .year(2024)
                        .hour(12)
                        .minutes(30)
                        .seconds(0)
                        .build())
                .units(null)
                .unitValue(null)
                .direction(null)
                .reference(null)
                .build();

        EventSchedule result = eventScheduleService.createOrUpdate(fixedSchedule);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testCreateOrUpdate_InvalidFixedSchedule() {
        EventSchedule fixedSchedule = EventSchedule.builder()
                .type(ScheduleType.FIXED)
                .date(SimpleTimestamp.builder()
                        .day(15)
                        .month(9)
                        .year(2024)
                        .hour(12)
                        .minutes(30)
                        .seconds(0)
                        .build())
                .units(null)
                .unitValue(5.0)
                .direction(null)
                .reference(null)
                .build();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            eventScheduleService.createOrUpdate(fixedSchedule);
        });

        String expectedMessage = messageSource.getMessage(MessagePropertiesKeys.ERROR_UNIT_VALUE_FIXED_SCHEDULE_MUST_BE_NULL, null, Locale.getDefault());
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void testCreateOrUpdate_InvalidRelativeSchedule() {
        EventSchedule relativeSchedule = EventSchedule.builder()
                .type(ScheduleType.RELATIVE)
                .date(SimpleTimestamp.builder()
                        .day(15)
                        .month(9)
                        .year(2024)
                        .hour(12)
                        .minutes(30)
                        .seconds(0)
                        .build())
                .units(null)
                .unitValue(5.0)
                .direction(null)
                .reference(null)
                .build();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            eventScheduleService.createOrUpdate(relativeSchedule);
        });

        String expectedMessage = messageSource.getMessage(MessagePropertiesKeys.ERROR_DATE_RELATIVE_SCHEDULE_MUST_BE_NULL, null, Locale.getDefault());
        Assertions.assertTrue(exception.getMessage().contains(expectedMessage));
    }
}