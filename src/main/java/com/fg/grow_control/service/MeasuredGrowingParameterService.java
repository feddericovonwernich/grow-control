package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.MeasuredGrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AssistantToolProvider
public class MeasuredGrowingParameterService extends BasicService<MeasuredGrowingParameter, Long, GrowingParameterRepository> {

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    private OptimalGrowingParameterService optimalGrowingParameterService;

    @Autowired
    public MeasuredGrowingParameterService(GrowingParameterRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterService_createOrUpdate",
            description = "Creates or updates a GrowingParameter object.",
            parameterClass = MeasuredGrowingParameter.class )
    public MeasuredGrowingParameter createOrUpdate(MeasuredGrowingParameter growingParameter) {

        if(growingParameter.getGrowingParameterType() != null && growingParameter.getGrowingParameterType().getId() == null) {
            growingParameterTypeService.createOrUpdate(growingParameter.getGrowingParameterType());
        }

        if (growingParameter.getMeasurementDevice() != null && growingParameter.getMeasurementDevice().getId() == null){
            measurementDeviceService.createOrUpdate(growingParameter.getMeasurementDevice());
        }
        if (growingParameter.getOptimalGrowingParameters() != null && !growingParameter.getOptimalGrowingParameters().isEmpty()) {
            growingParameter.getOptimalGrowingParameters().forEach(optimalGrowingParameter -> {
                if (optimalGrowingParameter.getId() == null) {
                    optimalGrowingParameterService.createOrUpdate(optimalGrowingParameter);
                }
            });
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
    public MeasuredGrowingParameter getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterService_getAll", description = "Retrieves all GrowingParameter objects.", parameters = "{}")
    public Page<MeasuredGrowingParameter> getAll(Pageable pageable) {
        return super.getAll(pageable);
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
