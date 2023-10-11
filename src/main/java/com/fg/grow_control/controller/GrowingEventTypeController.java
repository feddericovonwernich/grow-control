package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.service.GrowingEventTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growingEventType")
public class GrowingEventTypeController {

    @Autowired
    private GrowingEventTypeService growingEventTypeService;

    @GetMapping
    public ResponseEntity<List<GrowingEventType>> getAllGrowingEvent() {
        List<GrowingEventType> growingEventTypes = growingEventTypeService.getAllGrowingEventType();
        return new ResponseEntity<>(growingEventTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowingEventType> getGrowingEventTypeById(@PathVariable Long id) {
        GrowingEventType growingEventType = growingEventTypeService.getGrowingEventTypeById(id);
        if (growingEventType != null) {
            return new ResponseEntity<>(growingEventType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PutMapping
    public ResponseEntity<GrowingEventType> createOrUpdateGrowingEventType(@RequestBody GrowingEventType growingEventType) {
        GrowingEventType createdGrowingEventType = growingEventTypeService.createOrUpdateGrowingEventType(growingEventType);
        return new ResponseEntity<>(createdGrowingEventType, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingEventType(@PathVariable Long id) {
        try {
            growingEventTypeService.deleteGrowingEventType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
