package com.fg.grow_control.service;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.repository.EventScheduleRepository;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// TODO Create Function annotations.
public class EventScheduleService extends BasicService<EventSchedule, Long, EventScheduleRepository> {
    public EventScheduleService(EventScheduleRepository repository) {
        super(repository);
    }

    @Override
    public EventSchedule createOrUpdate(EventSchedule object) {
        return super.createOrUpdate(object);
    }

    @Override
    public EventSchedule getById(Long aLong) throws EntityNotFoundException {
        return super.getById(aLong);
    }

    @Override
    @FunctionDefinition(name = "EventScheduleService_getAll", description = "Retrieves all EventSchedule objects.", parameters = "{}")
    public Page<EventSchedule> getAll(int pageNumber, int pageSize) {
        return super.getAll(pageNumber, pageSize);
    }

    @Override
    public void deleteById(Long aLong) throws EntityNotFoundException {
        super.deleteById(aLong);
    }

    public Optional<EventSchedule> findByDateAndType(SimpleTimestamp simpleTimestamp, ScheduleType scheduleType) {
        return repository.findByDateAndType(simpleTimestamp,scheduleType);
    }
}
