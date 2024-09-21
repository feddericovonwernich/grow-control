package com.fg.grow_control.validator;

import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventScheduleValidator implements Validator {

    // This method checks whether the class to be validated is an EventSchedule.
    @Override
    public boolean supports(Class<?> clazz) {
        return EventSchedule.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EventSchedule eventSchedule = (EventSchedule) target;

        if (eventSchedule.getType() == ScheduleType.FIXED) {
            // For the FIXED type: Only the "date" field is required, other fields must be null.
            if (eventSchedule.getDate() == null) {
                errors.rejectValue("date", "Date is required when schedule type is FIXED.");
            }
            if (eventSchedule.getUnits() != null) {
                errors.rejectValue("units", "Units must be null when schedule type is FIXED.");
            }
            if (eventSchedule.getUnitValue() != null) {
                errors.rejectValue("unitValue", "Unit value must be null when schedule type is FIXED.");
            }
            if (eventSchedule.getDirection() != null) {
                errors.rejectValue("direction", "Direction must be null when schedule type is FIXED.");
            }
            if (eventSchedule.getReference() != null) {
                errors.rejectValue("reference", "Reference must be null when schedule type is FIXED.");
            }

        } else if (eventSchedule.getType() == ScheduleType.RELATIVE) {
            // For the RELATIVE type: "units", "unitValue", "direction", and "reference" are required;
            // "date" must be null.
            if (eventSchedule.getDate() != null) {
                errors.rejectValue("date", "Date must be null when schedule type is RELATIVE.");
            }
            if (eventSchedule.getUnits() == null) {
                errors.rejectValue("units", "Units are required when schedule type is RELATIVE.");
            }
            if (eventSchedule.getUnitValue() == null) {
                errors.rejectValue("unitValue", "Unit value is required when schedule type is RELATIVE.");
            }
            if (eventSchedule.getDirection() == null) {
                errors.rejectValue("direction", "Direction is required when schedule type is RELATIVE.");
            }
            if (eventSchedule.getReference() == null) {
                errors.rejectValue("reference", "Reference is required when schedule type is RELATIVE.");
            }
        }
    }
}