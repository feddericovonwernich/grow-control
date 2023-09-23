package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
import com.fg.grow_control.repository.GrowStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GrowCycleService {

    @Autowired
    private GrowCycleRepository growCycleRepository;

    @Autowired
    private GrowStageRepository growStageRepository;

    @Transactional
    public GrowCycle createOrUpdateGrowCycle(GrowCycle growCycle) {

        return growCycleRepository.save(growCycle);
    }
    public GrowCycle getGrowCycleById(Long id) throws EntityNotFoundException {
        Optional<GrowCycle> response = growCycleRepository.findById(id);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new EntityNotFoundException("No se encontró la entidad con el ID: " + id);
        }
    }
    public List<GrowCycle> getAllGrowCycles() {
        return growCycleRepository.findAll();
    }

    @Transactional
    public void deleteGrowCycle(Long id) throws EntityNotFoundException {
        Optional<GrowCycle> response = growCycleRepository.findById(id);
        if (response.isPresent()) {
            growCycleRepository.delete(response.get());
        } else {
            throw new EntityNotFoundException("No se pudo eliminar el ciclo de cultivo con ID " + id);
        }
    }
}
