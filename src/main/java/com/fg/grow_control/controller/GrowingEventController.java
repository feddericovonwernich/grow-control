package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.repository.GrowingEventRepository;
import com.fg.grow_control.service.GrowingEventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growingEvent")
@PreAuthorize("permitAll()")
public class GrowingEventController extends BasicController<GrowingEvent, Long, GrowingEventRepository, GrowingEventService> {
    public GrowingEventController(GrowingEventService service) {
        super(service);
    }
}

