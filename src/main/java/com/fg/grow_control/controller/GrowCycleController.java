package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.service.GrowCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/growCycle")
public class GrowCycleController {

    @Autowired
    private GrowCycleService growCycleService;

    @GetMapping
    public ResponseEntity<List<GrowCycle>> getAllGrowCycles() {
        List<GrowCycle> growCycles = growCycleService.getAllGrowCycles();
        return new ResponseEntity<>(growCycles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowCycle> getGrowCycleById(@PathVariable Long id) {
        GrowCycle growCycle = growCycleService.getGrowCycleById(id);
        if (growCycle != null) {
            return new ResponseEntity<>(growCycle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GrowCycle> createOrUpdateGrowCycle(@RequestBody GrowCycle growCycle) {
        GrowCycle createdGrowCycle = growCycleService.createOrUpdateGrowCycle(growCycle);
        return new ResponseEntity<>(createdGrowCycle, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowCycle(@PathVariable Long id) {
        try {
            growCycleService.deleteGrowCycle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}