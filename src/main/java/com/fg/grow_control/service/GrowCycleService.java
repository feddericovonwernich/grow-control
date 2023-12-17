package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowCycleService extends BasicService<GrowCycle, Long, GrowCycleRepository>{

    @Autowired
    private GrowStageService growStageService;

    @Autowired
    public GrowCycleService (GrowCycleRepository repository) { super(repository);}

    @Override
    public GrowCycle createOrUpdate(GrowCycle object) {

        if (object.getGrowStages() != null && !object.getGrowStages().isEmpty()) {
            object.getGrowStages().forEach(growStage -> {
                if (growStage.getId() == null) {
                    growStageService.createOrUpdate(growStage);
                }
            });
        }

        return super.createOrUpdate(object);
    }
}
