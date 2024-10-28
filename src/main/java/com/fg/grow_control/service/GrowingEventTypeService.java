package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.repository.GrowingEventTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AssistantToolProvider
public class GrowingEventTypeService extends BasicService<GrowingEventType, Long, GrowingEventTypeRepository> {

    @Autowired
    public GrowingEventTypeService(GrowingEventTypeRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    @FunctionDefinition(name = "GrowingEventTypeService_createOrUpdate",
            description = "Creates or updates a GrowingEventType object.",
            parameterClass = GrowingEventType.class)
    public GrowingEventType createOrUpdate(GrowingEventType growingEventType) {
        return super.createOrUpdate(growingEventType);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventTypeService_getById", description = "Retrieves a GrowingEventType object by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "number",
                        "description": "The ID of the GrowingEventType object to retrieve."
                    }
                },
                "required": ["id"]
            }
        """)
    public GrowingEventType getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventTypeService_getAll", description = "Retrieves all GrowingEventType objects.", parameters = "{}")
    public Page<GrowingEventType> getAll(Pageable pageable) {
        return super.getAll(pageable);
    }

    @Override
    @Transactional
    @FunctionDefinition(name = "GrowingEventTypeService_deleteById", description = "Deletes a GrowingEventType object by its Id.", parameters = """
            {
                "type": "object",
                "properties": {
                    "id": {
                        "type": "number",
                        "description": "The ID of the GrowingEventType object to delete."
                    }
                },
                "required": ["id"]
            }
        """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }

    public Optional<GrowingEventType> findByName(String name) {
        return repository.findByName(name);
    }
}
