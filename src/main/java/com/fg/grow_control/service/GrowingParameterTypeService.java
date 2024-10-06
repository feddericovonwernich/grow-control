package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.repository.GrowingParameterTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AssistantToolProvider
public class GrowingParameterTypeService extends BasicService<GrowingParameterType, Long, GrowingParameterTypeRepository> {

    @Autowired
    public GrowingParameterTypeService(GrowingParameterTypeRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_createOrUpdate",
            description = "Creates or updates a GrowingParameterType object.",
            parameterClass = GrowingParameterType.class)
    public GrowingParameterType createOrUpdate(GrowingParameterType growingParameterType) {
        return super.createOrUpdate(growingParameterType);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_getByID", description = "Retrieves a GrowingParameterType object by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "number",
                        "description": "The ID of the GrowingParameterType to retrieve."
                    }
                },
                "required": ["id"]
            }
        """)
    public GrowingParameterType getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_getAll", description = "Retrieves all GrowingParameterType objects.",
            parameters = """
                        {}
                    """)
    public List<GrowingParameterType> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "GrowingParameterTypeService_deleteById", description = "Deletes a GrowingParameterType object by its Id.",
        parameters = """
                {
                    "type": "object",
                    "properties": {
                        "id": {
                            "type": "number",
                            "description": "The ID of the GrowingParameterType to delete."
                        }
                    },
                    "required": ["id"]
                }
            """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }

    public Optional<GrowingParameterType> findByName(String testType) {
        return repository.findByName(testType);
    }
}
