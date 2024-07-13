package com.fg.grow_control.service;

import com.fg.grow_control.entity.schedule.RangeSchedule;
import com.fg.grow_control.repository.RangeScheduleRepository;
import org.springframework.stereotype.Service;

@Service
// TODO Create Function annotations.
public class RangeScheduleService extends BasicService<RangeSchedule, Long, RangeScheduleRepository> {
    public RangeScheduleService(RangeScheduleRepository repository) {
        super(repository);
    }
}
