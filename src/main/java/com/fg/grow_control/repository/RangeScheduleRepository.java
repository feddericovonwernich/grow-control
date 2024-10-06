package com.fg.grow_control.repository;

import com.fg.grow_control.entity.schedule.RangeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RangeScheduleRepository extends JpaRepository<RangeSchedule, Long> {
}
