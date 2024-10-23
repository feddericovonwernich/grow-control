package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.entity.DeviceTrigger;
import com.fg.grow_control.repository.DeviceTriggerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AssistantToolProvider
public class DeviceTriggerService extends BasicService<DeviceTrigger, Long, DeviceTriggerRepository> {

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
    @FunctionDefinition(name = "DeviceTriggerService_createOrUpdate",
            description = "Creates or updates a DeviceTrigger object.",
            parameterClass = DeviceTrigger.class)
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
    public Page<DeviceTrigger> getAll(Pageable pageable) {
        return super.getAll(pageable);
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

}
