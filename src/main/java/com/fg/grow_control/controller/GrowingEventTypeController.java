package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.repository.GrowingEventTypeRepository;
import com.fg.grow_control.service.GrowingEventTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/growingEventType")
public class GrowingEventTypeController extends BasicController<GrowingEventType, Long, GrowingEventTypeRepository, GrowingEventTypeService> {
    public GrowingEventTypeController(GrowingEventTypeService service) {
        super(service);
    }
}
