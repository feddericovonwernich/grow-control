package com.fg.grow_control.service;

import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.MeasurementDeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AssistantToolProvider
public class MeasurementDeviceService extends BasicService<MeasurementDevice, Long, MeasurementDeviceRepository>{

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Autowired
    public MeasurementDeviceService(MeasurementDeviceRepository repository) {
        super(repository);
    }

    @Override
    public MeasurementDevice createOrUpdate(MeasurementDevice measurementDevice) {

        if (measurementDevice.getGrowingParameterType().getId() == null) {
            growingParameterTypeService.createOrUpdate(measurementDevice.getGrowingParameterType());
        }

        return super.createOrUpdate(measurementDevice);
    }

    @Override
    @FunctionDefinition(name = "getByID", description = "Retrieves a MeasurementDevice object with the given Id.",
            parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "long",
                      "description": "The ID of the object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public MeasurementDevice getById(Long aLong) throws EntityNotFoundException {
        return super.getById(aLong);
    }

}
