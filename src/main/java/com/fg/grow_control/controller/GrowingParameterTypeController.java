package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.repository.GrowingParameterRepository;
import com.fg.grow_control.repository.GrowingParameterTypeRepository;
import com.fg.grow_control.service.GrowingParameterTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growingParameterType")
public class GrowingParameterTypeController extends BasicController<GrowingParameterType, Long, GrowingParameterTypeRepository, GrowingParameterTypeService> {
    public GrowingParameterTypeController(GrowingParameterTypeService service) {
        super(service);
    }
}
