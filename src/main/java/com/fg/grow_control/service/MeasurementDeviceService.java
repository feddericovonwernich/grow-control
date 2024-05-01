package com.fg.grow_control.service;

import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.MeasurementDeviceRepository;
import com.fg.grow_control.service.assistant.AssistantToolProvider;
import com.fg.grow_control.service.assistant.FunctionDefinition;
import com.fg.grow_control.service.assistant.ToolParameterAware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
@AssistantToolProvider
public class MeasurementDeviceService extends BasicService<MeasurementDevice, Long, MeasurementDeviceRepository> implements ToolParameterAware {

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Autowired
    public MeasurementDeviceService(MeasurementDeviceRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "MeasurementDeviceService_createOrUpdate", description = "Creates or updates a MeasurementDevice object.",
            parameters = """
                        {
                          "type": "object",
                          "properties": {
                            "measurementDevice": {
                              "type": "object",
                              "properties": {
                                "id": {
                                  "type": "number",
                                  "description": "The unique identifier of the MeasurementDevice. Null if new."
                                },
                                "growingParameterType": {
                                  "type": "object",
                                  "properties": {
                                    "id": {
                                      "type": "number",
                                      "description": "The ID of the GrowingParameterType associated with this device."
                                    }
                                  },
                                  "required": []
                                }
                              },
                              "required": ["growingParameterType"]
                            }
                          },
                          "required": ["measurementDevice"]
                        }
                    """)
    public MeasurementDevice createOrUpdate(MeasurementDevice measurementDevice) {

        if (measurementDevice.getGrowingParameterType().getId() == null) {
            growingParameterTypeService.createOrUpdate(measurementDevice.getGrowingParameterType());
        }

        return super.createOrUpdate(measurementDevice);
    }

    @Override
    @FunctionDefinition(name = "MeasurementDeviceService_getById", description = "Retrieves a MeasurementDevice object with the given Id.",
            parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public MeasurementDevice getById(Long aLong) throws EntityNotFoundException {
        return super.getById(aLong);
    }

    @Override
    @FunctionDefinition(name = "MeasurementDeviceService_getAll", description = "Retrieves all MeasurementDevice objects.",
            parameters = "{}")
    public List<MeasurementDevice> getAll() {
        return super.getAll();
    }

    @Override
    @Transactional
    @FunctionDefinition(name = "MeasurementDeviceService_deleteById", description = "Deletes a MeasurementDevice object with the given Id.",
            parameters = """
                        {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The ID of the object to delete."
                            }
                          },
                          "required": ["id"]
                        }
                    """)
    public void deleteById(Long aLong) throws EntityNotFoundException {
        super.deleteById(aLong);
    }

    @Override
    public List<Object> getParametersForFunction(String functionName, String parametersString) {
        switch (functionName) {
            case "MeasurementDeviceService_getById", "MeasurementDeviceService_deleteById":
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);

            case "MeasurementDeviceService_createOrUpdate":
                Gson gson = new Gson();
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                MeasurementDevice measurementDevice = gson.fromJson(jsonObj.get("measurementDevice"), MeasurementDevice.class);
                return Collections.singletonList(createOrUpdate(measurementDevice));

            default:
                return Collections.emptyList();
        }
    }

}
