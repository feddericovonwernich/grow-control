package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.entity.GrowingParameterValueTime;
import com.fg.grow_control.repository.GrowingParameterRepository;
import com.fg.grow_control.repository.GrowingParameterValueTimeRepository;
import com.fg.grow_control.service.GrowingParameterValueTimeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growingParameterValueTime")
public class GrowingParameterValueTimeController extends BasicController<GrowingParameterValueTime, Long, GrowingParameterValueTimeRepository, GrowingParameterValueTimeService> {
    public GrowingParameterValueTimeController(GrowingParameterValueTimeService service) {
        super(service);
    }
}
