package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AssistantToolProvider
public class GrowingParameterService extends BasicService<GrowingParameter, Long, GrowingParameterRepository> {

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    public GrowingParameterService(GrowingParameterRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterService_createOrUpdate", description = "Creates or updates a GrowingParameter object.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "growingParameter": {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The unique identifier of the GrowingParameter. Null if new."
                            },
                            "value": {
                              "type": "number",
                              "description": "The value of the growing parameter."
                            },
                            "growStage": {
                              "type": "object",
                              "properties": {
                                "id": {
                                  "type": "number",
                                  "description": "ID of the GrowStage associated."
                                }
                              },
                              "required": ["id"]
                            },
                            "growingParameterType": {
                              "type": "object",
                              "properties": {
                                "id": {
                                  "type": "number",
                                  "description": "ID of the GrowingParameterType associated."
                                }
                              },
                              "required": ["id"]
                            }
                          },
                          "required": ["value", "growStage", "growingParameterType"]
                        }
                      },
                      "required": ["growingParameter"]
                    }
            """)
    public GrowingParameter createOrUpdate(GrowingParameter growingParameter) {
        if(growingParameter.getGrowingParameterType() != null && growingParameter.getGrowingParameterType().getId() == null) {
            growingParameterTypeService.createOrUpdate(growingParameter.getGrowingParameterType());
        }
        if (growingParameter.getMeasurementDevice() != null && growingParameter.getMeasurementDevice().getId() == null){
            measurementDeviceService.createOrUpdate(growingParameter.getMeasurementDevice());
        }

        return super.createOrUpdate(growingParameter);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterService_getById", description = "Retrieves a GrowingParameter object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingParameter object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public GrowingParameter getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterService_getAll", description = "Retrieves all GrowingParameter objects.", parameters = "{}")
    public List<GrowingParameter> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterService_deleteById", description = "Deletes a GrowingParameter object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingParameter object to delete."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }
}
