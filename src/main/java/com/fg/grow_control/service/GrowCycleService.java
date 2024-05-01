package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
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
public class GrowCycleService extends BasicService<GrowCycle, Long, GrowCycleRepository> implements ToolParameterAware {

    @Autowired
    private GrowStageService growStageService;

    @Autowired
    public GrowCycleService(GrowCycleRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowCycleService_createOrUpdate", description = "Creates or updates a GrowCycle object.", parameters = """
                        {
                          "type": "object",
                          "properties": {
                            "growCycle": {
                              "type": "object",
                              "properties": {
                                "id": {
                                  "type": "number",
                                  "description": "The unique identifier of the GrowCycle. Null if new."
                                },
                                "description": {
                                  "type": "string",
                                  "description": "The description of the GrowCycle."
                                },
                                "date_start": {
                                  "type": "string",
                                  "description": "The start date of the GrowCycle."
                                },
                                "date_end": {
                                  "type": "string",
                                  "description": "The end date of the GrowCycle."
                                },
                                "growStages": {
                                  "type": "array",
                                  "items": {
                                    "type": "object"
                                  },
                                  "description": "The stages of the GrowCycle."
                                }
                              },
                              "required": ["description", "date_start", "date_end"]
                            }
                          },
                          "required": ["growCycle"]
                        }
                """)
    public GrowCycle createOrUpdate(GrowCycle growCycle) {
        if (growCycle.getGrowStages() != null && !growCycle.getGrowStages().isEmpty()) {
            growCycle.getGrowStages().forEach(growStage -> {
                if (growStage.getId() == null) {
                    growStageService.createOrUpdate(growStage);
                }
            });
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
    public List<GrowCycle> getAll() {
        return super.getAll();
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

    @Override
    public List<Object> getParametersForFunction(String functionName, String parametersString) {
        switch (functionName) {
            case "GrowCycleService_getByID", "GrowCycleService_deleteById":
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);
            case "GrowCycleService_createOrUpdate":
                Gson gson = new Gson();
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                GrowCycle growCycle = gson.fromJson(jsonObj.getAsJsonObject("growCycle"), GrowCycle.class);
                return Collections.singletonList(growCycle);
            default:
                return Collections.emptyList();
        }
    }

}