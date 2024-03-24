package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.repository.GrowingEventRepository;
import com.fg.grow_control.service.GrowingEventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growingEvent")
public class GrowingEventController extends BasicController<GrowingEvent, Long, GrowingEventRepository, GrowingEventService> {
    public GrowingEventController(GrowingEventService service) {
        super(service);
    }
}

