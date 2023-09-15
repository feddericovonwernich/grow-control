package com.fg.growcontrol.service;

import com.fg.growcontrol.entity.GrowStage;
import com.fg.growcontrol.repository.GrowStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
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
