package com.fg.grow_control.validator;

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
                errors.rejectValue("date", "error.date.required");
            }
            if (eventSchedule.getUnits() != null) {
                errors.rejectValue("units", "error.units.mustBeNull");
            }
            if (eventSchedule.getUnitValue() != null) {
                errors.rejectValue("unitValue", "error.unitValue.mustBeNull");
            }
            if (eventSchedule.getDirection() != null) {
                errors.rejectValue("direction", "error.direction.mustBeNull");
            }
            if (eventSchedule.getReference() != null) {
                errors.rejectValue("reference", "error.reference.mustBeNull");
            }

        } else if (eventSchedule.getType() == ScheduleType.RELATIVE) {
            if (eventSchedule.getDate() != null) {
                errors.rejectValue("date", "error.date.mustBeNull");
            }
            if (eventSchedule.getUnits() == null) {
                errors.rejectValue("units", "error.units.required");
            }
            if (eventSchedule.getUnitValue() == null) {
                errors.rejectValue("unitValue", "error.unitValue.required");
            }
            if (eventSchedule.getDirection() == null) {
                errors.rejectValue("direction", "error.direction.required");
            }
            if (eventSchedule.getReference() == null) {
                errors.rejectValue("reference", "error.reference.required");
            }
        }
    }
}