package com.fg.grow_control.service;

import com.fg.grow_control.entity.MeasurementDevice;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.repository.ActionDeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AssistantToolProvider
public class ActionDeviceService extends BasicService<ActionDevice, Long, ActionDeviceRepository> {

    public ActionDeviceService(ActionDeviceRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "ActionDeviceService_createOrUpdate",
            description = "Creates or updates an ActionDevice object.",
            parameterClass = ActionDevice.class)
    public ActionDevice createOrUpdate(ActionDevice actionDevice) {
        return super.createOrUpdate(actionDevice);
    }

    @Override
    @FunctionDefinition(name = "ActionDeviceService_getById", description = "Retrieves an ActionDevice object by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the ActionDevice object to retrieve."
                    }
                },
                "required": ["id"]
            }
        """)
    public ActionDevice getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "ActionDeviceService_getAll", description = "Retrieves all ActionDevice objects.", parameters = "{}")
    public Page<ActionDevice> getAll(int pageNumber, int pageSize) {
        return super.getAll(pageNumber, pageSize);
    }

    @Override
    @FunctionDefinition(name = "ActionDeviceService_deleteById", description = "Deletes an ActionDevice object by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "number",
                        "description": "The ID of the ActionDevice object to delete."
                    }
                },
                "required": ["id"]
            }
        """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }

    public Optional<ActionDevice> getActionDeviceByMeasurementDevice(MeasurementDevice measurementDevice) {
        return repository.findByWatchedMeasurement(measurementDevice);
    }
}
