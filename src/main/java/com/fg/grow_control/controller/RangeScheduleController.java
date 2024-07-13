package com.fg.grow_control.controller;

import com.fg.grow_control.entity.schedule.RangeSchedule;
import com.fg.grow_control.repository.RangeScheduleRepository;
import com.fg.grow_control.service.RangeScheduleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rangeSchedule")
public class RangeScheduleController extends BasicController<RangeSchedule, Long, RangeScheduleRepository, RangeScheduleService> {
    public RangeScheduleController(RangeScheduleService service) {
        super(service);
    }
}
