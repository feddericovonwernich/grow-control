package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.service.GrowStageTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/growStageType")
public class GrowStageTypeController {

    @Autowired
    private GrowStageTypeService growStageTypeService;

    @GetMapping
    public ResponseEntity<List<GrowStageType>> getAllGrowStageType() {
        List<GrowStageType> growStageTypes = growStageTypeService.getAllGrowStageType();
        return new ResponseEntity<>(growStageTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowStageType> getGrowStageTypeById(@PathVariable Long id) {
        GrowStageType growStageType = growStageTypeService.getGrowStageTypeById(id);
        if (growStageType != null) {
            return new ResponseEntity<>(growStageType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GrowStageType> createOrUpdateGrowStageType(@RequestBody GrowStageType growStageType) {
        GrowStageType createdGrowStageType = growStageTypeService.createOrUpdateGrowStageType(growStageType);
        return new ResponseEntity<>(createdGrowStageType, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowStageType(@PathVariable Long id) {
        try {
            growStageTypeService.deleteGrowStageType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
