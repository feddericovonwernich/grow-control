package com.fg.grow_control.controller;

import com.fg.grow_control.entity.schedule.EventSchedule;
import com.fg.grow_control.repository.EventScheduleRepository;
import com.fg.grow_control.service.EventScheduleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventSchedule")
public class EventScheduleController extends BasicController<EventSchedule, Long, EventScheduleRepository, EventScheduleService> {
    public EventScheduleController(EventScheduleService service) {
        super(service);
    }
}
