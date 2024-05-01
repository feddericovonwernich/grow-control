package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.repository.GrowingParameterTypeRepository;
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
public class GrowingParameterTypeService extends BasicService<GrowingParameterType, Long, GrowingParameterTypeRepository>  implements ToolParameterAware {

    @Autowired
    public GrowingParameterTypeService(GrowingParameterTypeRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_createOrUpdate", description = "Creates or updates a GrowingParameterType object.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "growingParameterType": {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The unique identifier of the GrowingParameterType. Null if new."
                        },
                        "name": {
                          "type": "string",
                          "description": "The name of the GrowingParameterType."
                        }
                      },
                      "required": ["name"]
                    }
                  },
                  "required": ["growingParameterType"]
                }
            """)
    public GrowingParameterType createOrUpdate(GrowingParameterType growingParameterType) {
        return super.createOrUpdate(growingParameterType);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_getByID", description = "Retrieves a GrowingParameterType object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingParameterType to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public GrowingParameterType getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_getAll", description = "Retrieves all GrowingParameterType objects.",
            parameters = """
                        {}
                    """)
    public List<GrowingParameterType> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_deleteById", description = "Deletes a GrowingParameterType object by its Id.",
            parameters = """
                        {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The ID of the GrowingParameterType to delete."
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
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
        switch (functionName) {
            case "GrowingParameterTypeService_createOrUpdate":
                return Collections.singletonList(gson.fromJson(jsonObj.getAsJsonObject("growingParameterType"), GrowingParameterType.class));

            case "GrowingParameterTypeService_getByID", "GrowingParameterTypeService_deleteById":
                Long id = jsonObj.get("id").getAsLong();
                return Collections.singletonList(id);

            default:
                return Collections.emptyList();
        }
    }

}
