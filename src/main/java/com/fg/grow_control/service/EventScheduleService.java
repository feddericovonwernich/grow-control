package com.fg.grow_control.service;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import com.fg.grow_control.repository.EventScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
