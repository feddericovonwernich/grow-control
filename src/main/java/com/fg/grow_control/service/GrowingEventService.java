package com.fg.grow_control.service;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.repository.GrowingEventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AssistantToolProvider
public class GrowingEventService extends BasicService<GrowingEvent, Long, GrowingEventRepository> {

    @Autowired
    private GrowingEventTypeService growingEventTypeService;

    @Autowired
    public GrowingEventService(GrowingEventRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventService_createOrUpdate", description = "Creates or updates a GrowingEvent object.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "growingEvent": {
                      "type": "object",
                      "properties": {
                        "id": {
                          "type": "number",
                          "description": "The unique identifier of the GrowingEvent. Null if new."
                        },
                        "description": {
                          "type": "string",
                          "description": "A description of the growing event."
                        },
                        "date": {
                          "type": "string",
                          "description": "The date of the growing event."
                        },
                        "growStage": {
                          "type": "number",
                          "description": "The ID of the GrowStage associated with this event."
                        },
                        "growingEventType": {
                          "type": "number",
                          "description": "The ID of the GrowingEventType associated with this event."
                        }
                      },
                      "required": ["description", "date", "growStage", "growingEventType"]
                    }
                  },
                  "required": ["growingEvent"]
                }
            """)
    public GrowingEvent createOrUpdate(GrowingEvent object) {
        if (object.getGrowingEventType() != null && object.getGrowingEventType().getId() == null) {
            growingEventTypeService.createOrUpdate(object.getGrowingEventType());
        }

        return super.createOrUpdate(object);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventService_getById", description = "Retrieves a GrowingEvent object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingEvent object to retrieve."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public GrowingEvent getById(Long id) throws EntityNotFoundException {
        return super.getById(id);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventService_getAll", description = "Retrieves all GrowingEvent objects.", parameters = "{}")
    public List<GrowingEvent> getAll() {
        return super.getAll();
    }

    @Override
    @FunctionDefinition(name = "GrowingEventService_deleteById", description = "Deletes a GrowingEvent object by its Id.", parameters = """
                {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "number",
                      "description": "The ID of the GrowingEvent object to delete."
                    }
                  },
                  "required": ["id"]
                }
            """)
    public void deleteById(Long id) throws EntityNotFoundException {
        super.deleteById(id);
    }
}
