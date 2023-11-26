package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.service.GrowCycleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/growCycle")
public class GrowCycleController {

    @Autowired
    private GrowCycleService growCycleService;

    @GetMapping
    public ResponseEntity<List<GrowCycle>> getAllGrowCycles() {
        List<GrowCycle> growCycles = growCycleService.getAll();
        return new ResponseEntity<>(growCycles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowCycle> getGrowCycleById(@PathVariable Long id) {
        GrowCycle growCycle = growCycleService.getById(id);
        if (growCycle != null) {
            return new ResponseEntity<>(growCycle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PutMapping
    public ResponseEntity<GrowCycle> createOrUpdateGrowCycle(@RequestBody GrowCycle growCycle) {
        GrowCycle createdGrowCycle = growCycleService.createOrUpdate(growCycle);
        return new ResponseEntity<>(createdGrowCycle, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowCycle(@PathVariable Long id) {
        try {
            growCycleService.deletebyId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
