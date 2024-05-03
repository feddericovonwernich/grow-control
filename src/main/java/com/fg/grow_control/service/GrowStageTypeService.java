package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.repository.GrowStageTypeRepository;
import com.fg.grow_control.service.assistant.AssistantToolProvider;
import com.fg.grow_control.service.assistant.FunctionDefinition;
import com.fg.grow_control.service.assistant.ToolParameterAware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AssistantToolProvider
public class GrowStageTypeService extends BasicService<GrowStageType, Long, GrowStageTypeRepository> implements ToolParameterAware {

    @Autowired
    public GrowStageTypeService(GrowStageTypeRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowStageTypeService_createOrUpdate", description = "Creates or updates a GrowStageType object.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "growStageType": {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The unique identifier of the GrowStageType. Null if new."
                            },
                            "name": {
                              "type": "string",
                              "description": "The name of the GrowStageType."
                            }
                          },
                          "required": ["name"]
                        }
                      },
                      "required": ["growStageType"]
                    }
                """)
    public GrowStageType createOrUpdate(GrowStageType growStageType) {
        return super.createOrUpdate(growStageType);
    }

    @Override
    @FunctionDefinition(name = "GrowStageTypeService_getById", description = "Retrieves a GrowStageType object by its ID.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The ID of the GrowStageType object to retrieve."
                        }
                      },
                      "required": ["id"]
                    }
                """)
    public GrowStageType getById(Long id) {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowStageTypeService_getAll", description = "Retrieves all GrowStageType objects.", parameters = "{}")
    public List<GrowStageType> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "GrowStageTypeService_deleteById", description = "Deletes a GrowStageType object by its ID.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The ID of the GrowStageType object to delete."
                        }
                      },
                      "required": ["id"]
                    }
                """)
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public List<Object> getParametersForFunction(String functionName, String parametersString) {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);

        switch (functionName) {
            case "GrowStageTypeService_getById", "GrowStageTypeService_deleteById":
                Long id = jsonObj.get("id").getAsLong();
                return Collections.singletonList(id);

            case "GrowStageTypeService_createOrUpdate":
                GrowStageType growStageType = gson.fromJson(jsonObj.getAsJsonObject("growStageType"), GrowStageType.class);
                return Collections.singletonList(growStageType);

            default:
                return Collections.emptyList();
        }
    }

}
