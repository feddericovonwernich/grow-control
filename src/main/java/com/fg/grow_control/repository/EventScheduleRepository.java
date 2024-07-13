package com.fg.grow_control.repository;

import com.fg.grow_control.entity.schedule.EventSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventScheduleRepository extends JpaRepository<EventSchedule, Long> {
}
