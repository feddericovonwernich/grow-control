package com.fg.growcontrol.service;

import com.fg.growcontrol.entity.GrowingParameterType;
import com.fg.growcontrol.repository.GrowingParameterTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GrowingParameterTypeService {
    @Autowired
    private GrowingParameterTypeRepository growingParameterTypeRepository;
    @Transactional
    public GrowingParameterType createOrUpdateGrowingParameterType(GrowingParameterType growingParameterType) {

        return growingParameterTypeRepository.save(growingParameterType);
    }
    public GrowingParameterType getGrowingParameterTypeById(Long id) throws EntityNotFoundException {
        Optional<GrowingParameterType> response = growingParameterTypeRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
    @Transactional
    public void deleteGrowingParameterType(Long id) throws EntityNotFoundException {
        Optional<GrowingParameterType> response = growingParameterTypeRepository.findById(id);
        if (response.isPresent()) {
            growingParameterTypeRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
