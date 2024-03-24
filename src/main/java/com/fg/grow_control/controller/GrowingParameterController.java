package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import com.fg.grow_control.service.GrowingParameterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/growingParameter")
public class GrowingParameterController extends BasicController<GrowingParameter, Long, GrowingParameterRepository, GrowingParameterService> {
    public GrowingParameterController(GrowingParameterService service) {
        super(service);
    }
}
