package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AssistantToolProvider
public class GrowCycleService extends BasicService<GrowCycle, Long, GrowCycleRepository> {

    @Autowired
    private GrowStageService growStageService;

    @Autowired
    private RangeScheduleService rangeScheduleService;

    @Autowired
    public GrowCycleService(GrowCycleRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowCycleService_createOrUpdate",
            description = "Creates or updates a GrowCycle object.",
            parameterClass = GrowCycle.class)
    public GrowCycle createOrUpdate(GrowCycle growCycle) {
        if (growCycle.getGrowStages() != null && !growCycle.getGrowStages().isEmpty()) {
            growCycle.getGrowStages().forEach(growStage -> {
                if (growStage.getId() == null) {
                    growStageService.createOrUpdate(growStage);
                }
            });
        }

        if (growCycle.getRangeSchedule() != null && growCycle.getRangeSchedule().getId() == null) {
            rangeScheduleService.createOrUpdate(growCycle.getRangeSchedule());
        }

        return super.createOrUpdate(growCycle);
    }

    @Override
    @FunctionDefinition(name = "GrowCycleService_getById", description = "Retrieves a GrowCycle by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "number",
                        "description": "The ID of the GrowCycle to retrieve."
                    }
                },
                "required": ["id"]
            }
        """)
    public GrowCycle getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowCycleService_getAll", description = "Retrieves all GrowCycle objects.", parameters = "{}")
    public Page<GrowCycle> getAll(int pageNumber, int pageSize) {
        return super.getAll(pageNumber, pageSize);
    }

    @Override
    @FunctionDefinition(name = "GrowCycleService_deleteById", description = "Deletes a GrowCycle by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "number",
                        "description": "The ID of the GrowCycle to delete."
                    }
                },
                "required": ["id"]
            }
        """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }

}