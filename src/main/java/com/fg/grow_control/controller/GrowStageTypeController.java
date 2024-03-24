package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.repository.GrowStageTypeRepository;
import com.fg.grow_control.service.GrowStageTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growStageType")
public class GrowStageTypeController extends BasicController<GrowStageType, Long, GrowStageTypeRepository, GrowStageTypeService>{
    public GrowStageTypeController(GrowStageTypeService service) {
        super(service);
    }
}
