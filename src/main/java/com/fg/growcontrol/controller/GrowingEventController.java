package com.fg.growcontrol.controller;

import com.fg.growcontrol.entity.GrowingEvent;
import com.fg.growcontrol.service.GrowingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/growingEvent")
public class GrowingEventController {
    @Autowired
    private GrowingEventService growingEventService;

    @GetMapping
    public ResponseEntity<List<GrowingEvent>> getAllGrowingEvent() {
        List<GrowingEvent> growingEvents = growingEventService.getAllGrowingEvent();
        return new ResponseEntity<>(growingEvents, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GrowingEvent> getGrowingEventById(@PathVariable Long id) {
        GrowingEvent growingEvent = growingEventService.getGrowingEventById(id);
        if (growingEvent != null) {
            return new ResponseEntity<>(growingEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<GrowingEvent> createOrUpdateGrowingEvent(@RequestBody GrowingEvent growingEvent) {
        GrowingEvent createdGrowingEvent = growingEventService.createOrUpdateGrowingEvent(growingEvent);
        return new ResponseEntity<>(createdGrowingEvent, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrowingEvent(@PathVariable Long id) {
        try {
            growingEventService.deleteGrowingEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

