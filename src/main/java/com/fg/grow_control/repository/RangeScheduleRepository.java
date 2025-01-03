package com.fg.grow_control.repository;

import com.fg.grow_control.entity.schedule.RangeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangeScheduleRepository extends JpaRepository<RangeSchedule, Long> {
}
