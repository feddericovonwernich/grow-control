package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.service.GrowingParameterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/growingParameter")
public class GrowingParameterController {

    @Autowired
    private GrowingParameterService growingParameterService;

    @GetMapping
    public ResponseEntity<List<GrowingParameter>> getAllGrowingParameter() {
        List<GrowingParameter> growingParameters = growingParameterService.getAll();
        return new ResponseEntity<>(growingParameters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowingParameter> getGrowingParameterById(@PathVariable Long id) {
        GrowingParameter growingParameter = growingParameterService.getById(id);
        if (growingParameter != null) {
            return new ResponseEntity<>(growingParameter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PutMapping
    public ResponseEntity<GrowingParameter> createOrUpdateGrowingParameter(@RequestBody GrowingParameter growingParameter) {
        GrowingParameter createdGrowingParameter
                = growingParameterService.createOrUpdate(growingParameter);
        return new ResponseEntity<>(createdGrowingParameter, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingParameter(@PathVariable Long id) {
        try {
            growingParameterService.deletebyId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
