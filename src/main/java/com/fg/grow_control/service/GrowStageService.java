package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.repository.GrowStageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AssistantToolProvider
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
    @FunctionDefinition(name = "GrowStageService_createOrUpdate",
            description = "Creates or updates a GrowStage object.",
            parameterClass = GrowStage.class)
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

    @Override
    @FunctionDefinition(name = "GrowStageService_getById", description = "Retrieves a GrowStage object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowStage object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public GrowStage getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowStageService_getAll", description = "Retrieves all GrowStage objects.", parameters = "{}")
    public List<GrowStage> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "GrowStageService_deleteById", description = "Deletes a GrowStage object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowStage object to delete."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }

}

