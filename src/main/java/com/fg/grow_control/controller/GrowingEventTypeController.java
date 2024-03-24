package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.repository.GrowingEventTypeRepository;
import com.fg.grow_control.service.GrowingEventTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growingEventType")
public class GrowingEventTypeController extends BasicController<GrowingEventType, Long, GrowingEventTypeRepository, GrowingEventTypeService> {
    public GrowingEventTypeController(GrowingEventTypeService service) {
        super(service);
    }
}
