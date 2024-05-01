package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.repository.GrowingEventTypeRepository;
import com.fg.grow_control.service.assistant.AssistantToolProvider;
import com.fg.grow_control.service.assistant.FunctionDefinition;
import com.fg.grow_control.service.assistant.ToolParameterAware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AssistantToolProvider
public class GrowingEventTypeService extends BasicService<GrowingEventType, Long, GrowingEventTypeRepository> implements ToolParameterAware {

    @Autowired
    public GrowingEventTypeService(GrowingEventTypeRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    @FunctionDefinition(name = "GrowingEventTypeService_createOrUpdate", description = "Creates or updates a GrowingEventType object.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "growingEventType": {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The unique identifier of the GrowingEventType. Null if new."
                        },
                        "name": {
                          "type": "string",
                          "description": "The name of the GrowingEventType."
                        }
                      },
                      "required": ["name"]
                    }
                  },
                  "required": ["growingEventType"]
                }
            """)
    public GrowingEventType createOrUpdate(GrowingEventType growingEventType) {
        return super.createOrUpdate(growingEventType);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventTypeService_getById", description = "Retrieves a GrowingEventType object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingEventType object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public GrowingEventType getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventTypeService_getAll", description = "Retrieves all GrowingEventType objects.", parameters = "{}")
    public List<GrowingEventType> getAll() {
        return super.getAll();
    }

    @Override
    @Transactional
    @FunctionDefinition(name = "GrowingEventTypeService_deleteById", description = "Deletes a GrowingEventType object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingEventType object to delete."
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
        switch (functionName) {
            case "GrowingEventTypeService_getById", "GrowingEventTypeService_deleteById":
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);
            case "GrowingEventTypeService_createOrUpdate":
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                GrowingEventType growingEventType = gson.fromJson(jsonObj.get("growingEventType"), GrowingEventType.class);
                return Collections.singletonList(growingEventType);
            case "GrowingEventTypeService_getAll":
                return Collections.emptyList();
            default:
                throw new IllegalArgumentException("Unsupported function name provided");
        }
    }

}
