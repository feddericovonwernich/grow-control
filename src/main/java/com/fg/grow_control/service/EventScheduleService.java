package com.fg.grow_control.service;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.repository.EventScheduleRepository;
import com.fg.grow_control.validator.EventScheduleValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// TODO Create Function annotations.
public class EventScheduleService extends BasicService<EventSchedule, Long, EventScheduleRepository> {
    private final EventScheduleValidator eventScheduleValidator;
    private final MessageSource messageSource;

    public EventScheduleService(EventScheduleRepository repository, EventScheduleValidator eventScheduleValidator, MessageSource messageSource) {
        super(repository);
        this.eventScheduleValidator = eventScheduleValidator;
        this.messageSource = messageSource;
    }

    @Override
    public EventSchedule createOrUpdate(EventSchedule object) {

        DataBinder binder = validateSchedule(object, eventScheduleValidator);
        BindingResult result = binder.getBindingResult();

        if (result.hasErrors()) {
            // Collect all error messages using MessageSource
            String errorMessage = result.getAllErrors().stream()
                    .map(error -> messageSource.getMessage(error.getCode(), null, Locale.getDefault()))
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessage);
        }
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
    public DataBinder validateSchedule(Object object, EventScheduleValidator eventScheduleValidator) {
        DataBinder binder = new DataBinder(object);
        binder.addValidators(eventScheduleValidator);
        binder.validate();
        return binder;
    }
}
