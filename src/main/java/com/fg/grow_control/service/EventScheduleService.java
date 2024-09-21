package com.fg.grow_control.service;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.repository.EventScheduleRepository;
import com.fg.grow_control.validator.EventScheduleValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.List;
import java.util.Optional;

@Service
// TODO Create Function annotations.
public class EventScheduleService extends BasicService<EventSchedule, Long, EventScheduleRepository> {
    private final EventScheduleValidator eventScheduleValidator;

    // Constructor injection for the validator
    public EventScheduleService(EventScheduleRepository repository, EventScheduleValidator eventScheduleValidator) {
        super(repository);
        this.eventScheduleValidator = eventScheduleValidator;
    }

    @Override
    public EventSchedule createOrUpdate(EventSchedule object) {

        // Perform validation before saving or updating
        DataBinder binder = new DataBinder(object);
        binder.addValidators(eventScheduleValidator);
        binder.validate();

        BindingResult result = binder.getBindingResult();

        if (result.hasErrors()) {
            // Handle validation errors by throwing an exception
            throw new IllegalArgumentException(result.getAllErrors().toString());
        }

        // If there are no errors, proceed to save the object
        return super.createOrUpdate(object);
    }

    @Override
    public EventSchedule getById(Long aLong) throws EntityNotFoundException {
        return super.getById(aLong);
    }

    @Override
    public List<EventSchedule> getAll() {
        return super.getAll();
    }

    @Override
    public void deleteById(Long aLong) throws EntityNotFoundException {
        super.deleteById(aLong);
    }

    public Optional<EventSchedule> findByDateAndType(SimpleTimestamp simpleTimestamp, ScheduleType scheduleType) {
        return repository.findByDateAndType(simpleTimestamp,scheduleType);
    }
}