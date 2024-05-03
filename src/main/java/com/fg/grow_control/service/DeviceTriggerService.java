package com.fg.grow_control.service;

import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.entity.DeviceTrigger;
import com.fg.grow_control.repository.DeviceTriggerRepository;
import com.fg.grow_control.service.assistant.AssistantToolProvider;
import com.fg.grow_control.service.assistant.FunctionDefinition;
import com.fg.grow_control.service.assistant.ToolParameterAware;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@AssistantToolProvider
public class DeviceTriggerService extends BasicService<DeviceTrigger, Long, DeviceTriggerRepository> implements ToolParameterAware {

    public DeviceTriggerService(DeviceTriggerRepository repository) {
        super(repository);
    }

    @FunctionDefinition(name = "DeviceTriggerService_getLastTriggerForDevice", description = "Retrieves the last trigger for a given ActionDevice.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "actionDevice": {
                      "type": "object",
                      "description": "The ActionDevice for which the last trigger is being retrieved."
                    }
                  },
                  "required": ["actionDevice"]
                }
            """)
    public DeviceTrigger getLastTriggerForDevice(ActionDevice actionDevice) {
        return repository.findTopByTriggeredDeviceOrderByIdDesc(actionDevice)
                .orElse(null);
    }

    @Override
    @FunctionDefinition(name = "DeviceTriggerService_createOrUpdate", description = "Creates or updates a DeviceTrigger object.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "deviceTrigger": {
                          "type": "object",
                          "properties": {
                            "id": {
                              "type": "number",
                              "description": "The unique identifier of the DeviceTrigger. Null if new."
                            },
                            "triggeredDevice": {
                              "type": "object",
                              "description": "The ActionDevice that has triggered this event."
                            },
                            "triggerValue": {
                              "type": "number",
                              "description": "The value at which the trigger is activated."
                            },
                            "triggerTime": {
                              "type": "string",
                              "description": "The timestamp of when the trigger occurred."
                            },
                            "completed": {
                              "type": "boolean",
                              "description": "Whether the action associated with the trigger has been completed."
                            }
                          },
                          "required": ["triggeredDevice", "triggerValue", "triggerTime", "completed"]
                        }
                      },
                      "required": ["deviceTrigger"]
                    }
            """)
    public DeviceTrigger createOrUpdate(DeviceTrigger deviceTrigger) {
        return super.createOrUpdate(deviceTrigger);
    }

    @Override
    @FunctionDefinition(name = "DeviceTriggerService_getById", description = "Retrieves a DeviceTrigger object by its Id.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The ID of the DeviceTrigger object to retrieve."
                        }
                      },
                      "required": ["id"]
                    }
            """)
    public DeviceTrigger getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "DeviceTriggerService_getAll", description = "Retrieves all DeviceTrigger objects.", parameters = "{}")
    public List<DeviceTrigger> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "DeviceTriggerService_deleteById", description = "Deletes a DeviceTrigger object by its Id.", parameters = """
                    {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The ID of the DeviceTrigger object to delete."
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
        Gson gson = new Gson();
        switch (functionName) {
            case "DeviceTriggerService_getById":
            case "DeviceTriggerService_deleteById": {
                Long id = ToolParameterAware.getIdParameter(parametersString);
                return Collections.singletonList(id);
            }
            case "DeviceTriggerService_createOrUpdate": {
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                JsonObject detailsObj = jsonObj.getAsJsonObject("deviceTrigger");
                // Assuming createOrUpdate method requires specific object types per functionName
                return Collections.singletonList(gson.fromJson(detailsObj, DeviceTrigger.class));
            }
            case "DeviceTriggerService_getLastTriggerForDevice": {
                JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
                JsonObject actionDeviceObj = jsonObj.getAsJsonObject("actionDevice");
                ActionDevice actionDevice = gson.fromJson(actionDeviceObj, ActionDevice.class);
                return Collections.singletonList(actionDevice);
            }
            default:
                return Collections.emptyList();
        }
    }

}
