package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingEventType;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.AssistantToolProvider;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FunctionDefinition;
import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.repository.GrowingEventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AssistantToolProvider
public class GrowingEventService extends BasicService<GrowingEvent, Long, GrowingEventRepository> {

    @Autowired
    private GrowingEventTypeService growingEventTypeService;

    @Autowired
    private EventScheduleService eventScheduleService;

    @Autowired
    public GrowingEventService(GrowingEventRepository repository) {
        super(repository);
    }

    @Override
    @FunctionDefinition(name = "GrowingEventService_createOrUpdate",
            description = "Creates or updates a GrowingEvent object.",
            parameterClass = GrowingEvent.class)
    public GrowingEvent createOrUpdate(GrowingEvent object) {

        if (object.getGrowingEventType() != null && object.getGrowingEventType().getId() == null) {
            growingEventTypeService.createOrUpdate(object.getGrowingEventType());
        }

        if (object.getEventSchedule() != null && object.getEventSchedule().getId() == null) {
            eventScheduleService.createOrUpdate(object.getEventSchedule());
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
    public Page<GrowingEvent> getAll(Pageable pageable) {
        return super.getAll(pageable);
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

    public Optional<GrowingEvent> findByEventTypeAndDescription(GrowingEventType growingEventType, String descriptionStringForTestUpdateEventScheduleGrowingEvent) {
        return repository.findByGrowingEventTypeAndDescription(growingEventType,descriptionStringForTestUpdateEventScheduleGrowingEvent);
    }
}
