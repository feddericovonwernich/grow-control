package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.repository.GrowStageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GrowStageTypeService {
    @Autowired
    private GrowStageTypeRepository growStageTypeRepository;
    @Transactional
    public GrowStageType createOrUpdateGrowStageType(GrowStageType growStageType) {

        return growStageTypeRepository.save(growStageType);
    }
    public GrowStageType getGrowStageTypeById(Long id) throws EntityNotFoundException {
        Optional<GrowStageType> response = growStageTypeRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
    @Transactional
    public void deleteGrowStageType(Long id) throws EntityNotFoundException {
        Optional<GrowStageType> response = growStageTypeRepository.findById(id);
        if (response.isPresent()) {
            growStageTypeRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
