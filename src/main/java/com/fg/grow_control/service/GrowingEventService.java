package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.repository.GrowingEventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrowingEventService {
    @Autowired
    private GrowingEventRepository growingEventRepository;

    @Transactional
    public GrowingEvent createOrUpdateGrowingEvent(GrowingEvent growingEvent) {
        return growingEventRepository.save(growingEvent);
    }

    public GrowingEvent getGrowingEventById(Long id) throws EntityNotFoundException {
        Optional<GrowingEvent> response = growingEventRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }

    public List<GrowingEvent> getAllGrowingEvent() {
        return growingEventRepository.findAll();
    }
    @Transactional
    public void deleteGrowingEvent(Long id) throws EntityNotFoundException {
        Optional<GrowingEvent> response = growingEventRepository.findById(id);
        if (response.isPresent()) {
            growingEventRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
