package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.repository.GrowStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class GrowStageService {
    @Autowired
    private GrowStageRepository growStageRepository;
    @Transactional
    public GrowStage createOrUpdateGrowStage(GrowStage growStage) {

        return growStageRepository.save(growStage);
    }
    public GrowStage getGrowStageById(Long id) throws EntityNotFoundException {
        Optional<GrowStage> response = growStageRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
    public List<GrowStage> getAllGrowStages() {
        return growStageRepository.findAll();
    }
    @Transactional
    public void deleteGrowStage(Long id) throws EntityNotFoundException {
        Optional<GrowStage> response = growStageRepository.findById(id);
        if (response.isPresent()) {
            growStageRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
}
