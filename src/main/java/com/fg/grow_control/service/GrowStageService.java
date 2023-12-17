package com.fg.grow_control.service;

import com.fg.grow_control.entity.*;
import com.fg.grow_control.repository.GrowStageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrowStageService extends BasicService<GrowStage, Long, GrowStageRepository> {

    @Autowired
    private GrowStageTypeService growStageTypeService;

    @Autowired
    private GrowingEventService growingEventService;

    @Autowired
    private GrowingParameterService growingParameterService;

    @Autowired
    public GrowStageService(GrowStageRepository repository) {
            super(repository);
        }

    @Override
    public GrowStage createOrUpdate(GrowStage object) {

        if (object.getGrowStageType() != null && object.getGrowStageType().getId() == null) {
            growStageTypeService.createOrUpdate(object.getGrowStageType());
        }

        if (object.getGrowingEvents() != null && !object.getGrowingEvents().isEmpty()) {
            object.getGrowingEvents().forEach(growingEvent -> {
                if (growingEvent.getId() == null) {
                    growingEventService.createOrUpdate(growingEvent);
                }
            });
        }

        if (object.getGrowingParameters() != null && !object.getGrowingParameters().isEmpty()) {
            object.getGrowingParameters().forEach(growingParameter -> {
                if (growingParameter.getId() == null) {
                    growingParameterService.createOrUpdate(growingParameter);
                }
            });
        }

        return super.createOrUpdate(object);
    }
}
