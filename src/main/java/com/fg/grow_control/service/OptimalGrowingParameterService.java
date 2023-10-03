package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.repository.OptimalGrowingParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class OptimalGrowingParameterService {
    @Autowired
    private OptimalGrowingParameterRepository optimalGrowingParameterRepository;
    @Transactional
    public OptimalGrowingParameter createOrUpdateOptimalGrowingParameter(OptimalGrowingParameter optimalGrowingParameter) {

        return optimalGrowingParameterRepository.save(optimalGrowingParameter);
    }
    public OptimalGrowingParameter getOptimalGrowingParameterById(Long id) throws EntityNotFoundException {
        Optional<OptimalGrowingParameter> response = optimalGrowingParameterRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
    public List<OptimalGrowingParameter> getAllOptimalGrowingParameter() {
        return optimalGrowingParameterRepository.findAll();
    }
    @Transactional
    public void deleteOptimalGrowingParameter(Long id) throws EntityNotFoundException {
        Optional<OptimalGrowingParameter> response = optimalGrowingParameterRepository.findById(id);
        if (response.isPresent()) {
            optimalGrowingParameterRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
