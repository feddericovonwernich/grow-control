package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
import com.fg.grow_control.service.GrowCycleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growCycle")
public class GrowCycleController extends BasicController<GrowCycle, Long, GrowCycleRepository, GrowCycleService> {
    public GrowCycleController(GrowCycleService service) {
        super(service);
    }
}
