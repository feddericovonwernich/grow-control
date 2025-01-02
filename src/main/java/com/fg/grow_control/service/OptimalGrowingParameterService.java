package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.repository.OptimalGrowingParameterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AssistantToolProvider
public class OptimalGrowingParameterService extends BasicService<OptimalGrowingParameter, Long, OptimalGrowingParameterRepository> {

    @Autowired
    private RangeScheduleService rangeScheduleService;

    @Autowired
    public OptimalGrowingParameterService(OptimalGrowingParameterRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "OptimalGrowingParameterService_createOrUpdate",
            description = "Creates or updates an OptimalGrowingParameter object.",
            parameterClass = OptimalGrowingParameter.class)
    public OptimalGrowingParameter createOrUpdate(OptimalGrowingParameter optimalGrowingParameter) {
        if (optimalGrowingParameter.getRangeSchedule().getId() == null) {
            rangeScheduleService.createOrUpdate(optimalGrowingParameter.getRangeSchedule());
        }
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
    public Page<OptimalGrowingParameter> getAll(int pageNumber, int pageSize) {
        return super.getAll(pageNumber, pageSize);
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

}
