package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowingParameterValueTime;
import com.fg.grow_control.service.GrowingParameterValueTimeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/growingParameterValueTime")
public class GrowingParameterValueTimeController {

    @Autowired
    private GrowingParameterValueTimeService growingParameterValueTimeService;

    @GetMapping
    public ResponseEntity<List<GrowingParameterValueTime>> getAllGrowingParameterValueTime() {
        List<GrowingParameterValueTime> growingParameterValueTimeServices
                = growingParameterValueTimeService.getAllGrowingParameterValueTime();
        return new ResponseEntity<>(growingParameterValueTimeServices, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GrowingParameterValueTime> getGrowingParameterValueTimeById(@PathVariable Long id) {
        GrowingParameterValueTime growingParameterValueTime
                = growingParameterValueTimeService.getGrowingParameterValueTimeById(id);
        if (growingParameterValueTime != null) {
            return new ResponseEntity<>(growingParameterValueTime, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PutMapping
    public ResponseEntity<GrowingParameterValueTime> createOrUpdateGrowingParameterValueTime(@RequestBody GrowingParameterValueTime growingParameterValueTime) {
        GrowingParameterValueTime createdGrowingParameterValueTime
                = growingParameterValueTimeService.createOrUpdateGrowingParameterValueTime(growingParameterValueTime);
        return new ResponseEntity<>(createdGrowingParameterValueTime, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingParameterValueTime(@PathVariable Long id) {
        try {
            growingParameterValueTimeService.deleteGrowingParameterValueTime(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
