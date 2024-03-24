package com.fg.grow_control.controller;

import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.repository.OptimalGrowingParameterRepository;
import com.fg.grow_control.service.OptimalGrowingParameterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/optimalGrowingParameter")
public class OptimalGrowingParameterController extends BasicController<OptimalGrowingParameter, Long, OptimalGrowingParameterRepository, OptimalGrowingParameterService> {
    public OptimalGrowingParameterController(OptimalGrowingParameterService service) {
        super(service);
    }
}
