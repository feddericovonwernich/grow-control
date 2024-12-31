package com.fg.grow_control.validator;

import com.fg.grow_control.entity.MessagePropertiesKeys;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventScheduleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return EventSchedule.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EventSchedule eventSchedule = (EventSchedule) target;
        if (eventSchedule.getType() == ScheduleType.FIXED) {

            if (eventSchedule.getDate() == null) {
                errors.rejectValue("date", MessagePropertiesKeys.ERROR_DATE_REQUIRED_FIXED_SCHEDULE);
            }
            if (eventSchedule.getUnits() != null) {
                errors.rejectValue("units", MessagePropertiesKeys.ERROR_UNITS_FIXED_SCHEDULE_MUST_BE_NULL);
            }
            if (eventSchedule.getUnitValue() != null) {
                errors.rejectValue("unitValue", MessagePropertiesKeys.ERROR_UNIT_VALUE_FIXED_SCHEDULE_MUST_BE_NULL);
            }
            if (eventSchedule.getDirection() != null) {
                errors.rejectValue("direction", MessagePropertiesKeys.ERROR_DIRECTION_FIXED_SCHEDULE_MUST_BE_NULL);
            }
            if (eventSchedule.getReference() != null) {
                errors.rejectValue("reference", MessagePropertiesKeys.ERROR_REFERENCE_FIXED_SCHEDULE_MUST_BE_NULL);
            }

        } else if (eventSchedule.getType() == ScheduleType.RELATIVE) {
            if (eventSchedule.getDate() != null) {
                errors.rejectValue("date", MessagePropertiesKeys.ERROR_DATE_RELATIVE_SCHEDULE_MUST_BE_NULL);
            }
            if (eventSchedule.getUnits() == null) {
                errors.rejectValue("units", MessagePropertiesKeys.ERROR_UNITS_RELATIVE_SCHEDULE_REQUIRED);
            }
            if (eventSchedule.getUnitValue() == null) {
                errors.rejectValue("unitValue", MessagePropertiesKeys.ERROR_UNIT_VALUE_RELATIVE_SCHEDULE_REQUIRED);
            }
            if (eventSchedule.getDirection() == null) {
                errors.rejectValue("direction", MessagePropertiesKeys.ERROR_DIRECTION_RELATIVE_SCHEDULE_REQUIRED);
            }
            if (eventSchedule.getReference() == null) {
                errors.rejectValue("reference", MessagePropertiesKeys.ERROR_REFERENCE_RELATIVE_SCHEDULE_REQUIRED);
            }
        }
    }
}
