package com.fg.grow_control.service;

import com.fg.grow_control.dto.DeviceReadingDTO;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.DeviceReadingRepository;
import com.fg.grow_control.service.assistant.AssistantToolProvider;
import com.fg.grow_control.service.assistant.FunctionDefinition;
import com.fg.grow_control.service.assistant.ToolParameterAware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Component
@AssistantToolProvider
public class DeviceReadingService extends BasicService<DeviceReading, Long, DeviceReadingRepository> implements ToolParameterAware {

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    public DeviceReadingService(DeviceReadingRepository repository) {
        super(repository);
    }

    public DeviceReading registerReading(DeviceReadingDTO deviceReadingDTO) {

        MeasurementDevice measurementDevice = measurementDeviceService.getById(deviceReadingDTO.getDeviceId());

        DeviceReading newReading = DeviceReading.builder()
                .measurementDevice(measurementDevice)
                .reading(deviceReadingDTO.getMeasurementValue())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        return this.createOrUpdate(newReading);
    }


    @FunctionDefinition(name = "DeviceReadingService_getLastReadingForDevice", description = "Retrieves the last reading for a specified MeasurementDevice.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "measurementDevice": {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The unique identifier of the MeasurementDevice."
                        }
                      },
                      "required": ["id"]
                    }
                  },
                  "required": ["measurementDevice"]
                }
            """)
    public DeviceReading getLastReadingForDevice(MeasurementDevice measurementDevice) {
        // Utilizing custom repository method to find the last DeviceReading for a given MeasurementDevice
        return repository.findTopByMeasurementDeviceOrderByIdDesc(measurementDevice)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No readings found for device with ID: " + measurementDevice.getId()));
    }

    @Override
    @FunctionDefinition(name = "DeviceReadingService_createOrUpdate", description = "Creates or updates a DeviceReading object.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "deviceReading": {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The unique identifier of the DeviceReading. Null if new."
                            },
                            "measurementDeviceId": {
                              "type": "number",
                              "description": "The ID of the MeasurementDevice associated with this reading."
                            },
                            "reading": {
                              "type": "number",
                              "description": "The value of the reading."
                            },
                            "timestamp": {
                              "type": "string",
                              "description": "The timestamp of the reading."
                            }
                          },
                          "required": ["measurementDeviceId", "reading", "timestamp"]
                        }
                      },
                      "required": ["deviceReading"]
                    }
            """)
    public DeviceReading createOrUpdate(DeviceReading deviceReading) {
        return super.createOrUpdate(deviceReading);
    }

    @Override
    @FunctionDefinition(name = "DeviceReadingService_getById", description = "Retrieves a DeviceReading object by its Id.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The ID of the DeviceReading object to retrieve."
                        }
                      },
                      "required": ["id"]
                    }
            """)
    public DeviceReading getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "DeviceReadingService_getAll", description = "Retrieves all DeviceReading objects.", parameters = "{}")
    public List<DeviceReading> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "DeviceReadingService_deleteById", description = "Deletes a DeviceReading object by its Id.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The ID of the DeviceReading object to delete."
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
            case "DeviceReadingService_getById", "DeviceReadingService_deleteById":
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);

            case "DeviceReadingService_createOrUpdate":
                Gson gson = new Gson();
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                JsonObject deviceReadingObj = jsonObj.getAsJsonObject("deviceReading");
                return Collections.singletonList(gson.fromJson(deviceReadingObj, DeviceReading.class));

            case "DeviceReadingService_getLastReadingForDevice":
                // TODO Surprisingly this may work.. but should refactor.
                Gson gson2 = new Gson();
                JsonObject jsonObj2 = gson2.fromJson(parametersString, JsonObject.class);
                JsonObject measurementDeviceObj = jsonObj2.getAsJsonObject("measurementDevice");
                Long deviceId = measurementDeviceObj.get("id").getAsLong();
                MeasurementDevice measurementDevice = measurementDeviceService.getById(deviceId);
                return Collections.singletonList(measurementDevice);

            default:
                return Collections.emptyList();
        }
    }

}
