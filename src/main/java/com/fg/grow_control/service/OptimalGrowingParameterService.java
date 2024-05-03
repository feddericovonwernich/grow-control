package com.fg.grow_control.service;

import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.repository.OptimalGrowingParameterRepository;
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
public class OptimalGrowingParameterService extends BasicService<OptimalGrowingParameter, Long, OptimalGrowingParameterRepository> implements ToolParameterAware {

    @Autowired
    public OptimalGrowingParameterService(OptimalGrowingParameterRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "OptimalGrowingParameterService_createOrUpdate", description = "Creates or updates an OptimalGrowingParameter object.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "optimalGrowingParameter": {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The unique identifier of the OptimalGrowingParameter. Null if new."
                            },
                            "date_start": {
                              "type": "string",
                              "description": "The start date of the optimal growing parameter period."
                            },
                            "date_end": {
                              "type": "string",
                              "description": "The end date of the optimal growing parameter period."
                            },
                            "growingParameter": {
                              "type": "number",
                              "description": "ID of the GrowingParameter associated."
                            }
                          },
                          "required": ["date_start", "date_end", "growingParameter"]
                        }
                      },
                      "required": ["optimalGrowingParameter"]
                    }
            """)
    public OptimalGrowingParameter createOrUpdate(OptimalGrowingParameter optimalGrowingParameter) {
        return super.createOrUpdate(optimalGrowingParameter);
    }

    @Override
    @FunctionDefinition(name = "OptimalGrowingParameterService_getById", description = "Retrieves an OptimalGrowingParameter object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the OptimalGrowingParameter object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public OptimalGrowingParameter getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "OptimalGrowingParameterService_getAll", description = "Retrieves all OptimalGrowingParameter objects.", parameters = "{}")
    public List<OptimalGrowingParameter> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "OptimalGrowingParameterService_deleteById", description = "Deletes an OptimalGrowingParameter object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the OptimalGrowingParameter object to delete."
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
            case "OptimalGrowingParameterService_getByID", "OptimalGrowingParameterService_deleteById":
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);

            case "OptimalGrowingParameterService_createOrUpdate":
                Gson gson = new Gson();
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                JsonObject optimalGrowingParameterObj = jsonObj.getAsJsonObject("optimalGrowingParameter");
                return Collections.singletonList(gson.fromJson(optimalGrowingParameterObj, OptimalGrowingParameter.class));

            default:
                return Collections.emptyList();
        }
    }

}
