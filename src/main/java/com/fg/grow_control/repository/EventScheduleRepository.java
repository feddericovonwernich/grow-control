package com.fg.grow_control.repository;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.entity.schedule.ScheduleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventScheduleRepository extends JpaRepository<EventSchedule, Long> {
    Optional<EventSchedule> findByDateAndType(SimpleTimestamp simpleTimestamp, ScheduleType scheduleType);
}

