package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.repository.GrowStageRepository;
import com.fg.grow_control.service.assistant.AssistantToolProvider;
import com.fg.grow_control.service.assistant.FunctionDefinition;
import com.fg.grow_control.service.assistant.ToolParameterAware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AssistantToolProvider
public class GrowStageService extends BasicService<GrowStage, Long, GrowStageRepository> implements ToolParameterAware {

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
    @FunctionDefinition(name = "GrowStageService_createOrUpdate", description = "Creates or updates a GrowStage object.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "growStage": {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The unique identifier of the GrowStage. Null if new."
                        },
                        "durationUnit": {
                          "type": "string",
                          "description": "The unit of duration for the GrowStage."
                        },
                        "durationValue": {
                          "type": "number",
                          "description": "The value of duration for the GrowStage."
                        },
                        "growStageType": {
                          "$ref": "#/definitions/GrowStageType",
                          "description": "The type of GrowStage."
                        },
                        "growCycle": {
                          "$ref": "#/definitions/GrowCycle",
                          "description": "The cycle that this GrowStage belongs to."
                        },
                        "growingEvents": {
                          "type": "array",
                          "items": {
                            "$ref": "#/definitions/GrowingEvent"
                          },
                          "description": "The events associated with this GrowStage."
                        },
                        "growingParameters": {
                          "type": "array",
                          "items": {
                            "$ref": "#/definitions/GrowingParameter"
                          },
                          "description": "The parameters associated with this GrowStage."
                        }
                      },
                      "required": ["durationUnit", "durationValue", "growStageType"]
                    }
                  },
                  "required": ["growStage"]
                }
            """)
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

    @Override
    public List<Object> getParametersForFunction (String functionName, String parametersString){
        switch (functionName) {
            case "GrowStageService_getById", "GrowStageService_deleteById":
                // Extract ID parameter for functions 'getById' and 'deleteById'
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);

            case "GrowStageService_createOrUpdate":
                // Parse growStage object for 'createOrUpdate' function
                Gson gson = new Gson();
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                JsonObject growStageObj = jsonObj.getAsJsonObject("growStage");
                GrowStage growStage = gson.fromJson(growStageObj, GrowStage.class);
                return Collections.singletonList(growStage);

            default:
                // Return empty list for unrecognized function names
                return Collections.emptyList();
        }
    }

}

