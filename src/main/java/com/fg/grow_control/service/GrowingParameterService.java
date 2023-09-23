package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GrowingParameterService {
    @Autowired
    private GrowingParameterRepository growingParameterRepository;
    @Transactional
    public GrowingParameter createOrUpdateGrowingParameter(GrowingParameter growingParameter) {

        return growingParameterRepository.save(growingParameter);
    }
    public GrowingParameter getGrowingParameterById(Long id) throws EntityNotFoundException {
        Optional<GrowingParameter> response = growingParameterRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
    @Transactional
    public void deleteGrowingParameter(Long id) throws EntityNotFoundException {
        Optional<GrowingParameter> response = growingParameterRepository.findById(id);
        if (response.isPresent()) {
            growingParameterRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
