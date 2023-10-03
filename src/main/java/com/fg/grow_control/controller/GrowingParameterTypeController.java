package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.service.GrowingParameterTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growingParameterType")
public class GrowingParameterTypeController {

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @GetMapping
    public ResponseEntity<List<GrowingParameterType>> getAllGrowingParameterTypes() {
        List<GrowingParameterType> growingParameterTypes = growingParameterTypeService.getAllGrowingParameterType();
        return new ResponseEntity<>(growingParameterTypes, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GrowingParameterType> getGrowingParameterTypeById(@PathVariable Long id) {
        GrowingParameterType growingParameterType = growingParameterTypeService.getGrowingParameterTypeById(id);
        if (growingParameterType != null) {
            return new ResponseEntity<>(growingParameterType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<GrowingParameterType> createOrUpdateGrowingParameterType(@RequestBody GrowingParameterType growingParameterType) {
        GrowingParameterType createdGrowingParameterType = growingParameterTypeService.createOrUpdateGrowingParameterType(growingParameterType);
        return new ResponseEntity<>(createdGrowingParameterType, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingParameterType(@PathVariable Long id) {
        try {
            growingParameterTypeService.deleteGrowingParameterType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
