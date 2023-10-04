package com.fg.grow_control.controller;

import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.service.GrowStageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/growStage")
public class GrowStageServiceController {

    @Autowired
    private GrowStageService growStageService;

    @GetMapping
    public ResponseEntity<List<GrowStage>> getAllGrowStage() {
        List<GrowStage> growStages = growStageService.getAllGrowStages();
        return new ResponseEntity<>(growStages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrowStage> getGrowStageById(@PathVariable Long id) {
        GrowStage growStage = growStageService.getGrowStageById(id);
        if (growStage != null) {
            return new ResponseEntity<>(growStage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PutMapping
    public ResponseEntity<GrowStage> createOrUpdateGrowStage(@RequestBody GrowStage growStage) {
        GrowStage createdGrowStage = growStageService.createOrUpdateGrowStage(growStage);
        return new ResponseEntity<>(createdGrowStage, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowStage(@PathVariable Long id) {
        try {
            growStageService.deleteGrowStage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
