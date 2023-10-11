package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameterValueTime;
import com.fg.grow_control.repository.GrowingParameterValueTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrowingParameterValueTimeService {
    @Autowired
    private GrowingParameterValueTimeRepository growingParameterValueTimeRepository;

    @Transactional
    public GrowingParameterValueTime createOrUpdateGrowingParameterValueTime(GrowingParameterValueTime growingParameterValueTime) {
        return growingParameterValueTimeRepository.save(growingParameterValueTime);
    }

    public GrowingParameterValueTime getGrowingParameterValueTimeById(Long id) throws EntityNotFoundException {
        Optional<GrowingParameterValueTime> response = growingParameterValueTimeRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }

    public List<GrowingParameterValueTime> getAllGrowingParameterValueTime() {
        return growingParameterValueTimeRepository.findAll();
    }

    @Transactional
    public void deleteGrowingParameterValueTime(Long id) throws EntityNotFoundException {
        Optional<GrowingParameterValueTime> response = growingParameterValueTimeRepository.findById(id);
        if (response.isPresent()) {
            growingParameterValueTimeRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
