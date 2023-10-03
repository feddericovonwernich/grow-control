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
        List<GrowingParameter> growingParameters = growingParameterService.getAllGrowingParameter();
        return new ResponseEntity<>(growingParameters, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GrowingParameter> getGrowingParameterById(@PathVariable Long id) {
        GrowingParameter growingParameter = growingParameterService.getGrowingParameterById(id);
        if (growingParameter != null) {
            return new ResponseEntity<>(growingParameter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<GrowingParameter> createOrUpdateGrowingParameter(@RequestBody GrowingParameter growingParameter) {
        GrowingParameter createdGrowingParameter = growingParameterService.createOrUpdateGrowingParameter(growingParameter);
        return new ResponseEntity<>(createdGrowingParameter, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingParameter(@PathVariable Long id) {
        try {
            growingParameterService.deleteGrowingParameter(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
