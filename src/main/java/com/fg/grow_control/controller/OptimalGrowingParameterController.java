package com.fg.grow_control.controller;

import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.service.OptimalGrowingParameterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/optimalGrowingParameter")
public class OptimalGrowingParameterController {

    @Autowired
    private OptimalGrowingParameterService optimalGrowingParameterService;

    @GetMapping
    public ResponseEntity<List<OptimalGrowingParameter>> getAllOptimalGrowingParameter() {
        List<OptimalGrowingParameter> optimalGrowingParameters = optimalGrowingParameterService.getAll();
        return new ResponseEntity<>(optimalGrowingParameters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptimalGrowingParameter> getOptimalGrowingParameterById(@PathVariable Long id) {
        OptimalGrowingParameter optimalGrowingParameter = optimalGrowingParameterService.getById(id);
        if (optimalGrowingParameter != null) {
            return new ResponseEntity<>(optimalGrowingParameter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<OptimalGrowingParameter> createOrUpdateOptimalGrowingParameter(@RequestBody OptimalGrowingParameter optimalGrowingParameter) {
        OptimalGrowingParameter createdOptimalGrowingParameter = optimalGrowingParameterService.createOrUpdate(optimalGrowingParameter);
        return new ResponseEntity<>(createdOptimalGrowingParameter, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOptimalGrowingParameter(@PathVariable Long id) {
        try {
            optimalGrowingParameterService.deletebyId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
