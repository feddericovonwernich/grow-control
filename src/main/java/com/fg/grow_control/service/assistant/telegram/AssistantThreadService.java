package com.fg.grow_control.service.assistant.telegram;

import com.fg.grow_control.service.BasicService;
import org.springframework.stereotype.Component;

@Component
public class AssistantThreadService extends BasicService<AssistantThread, Long, AssistantThreadRepository> {
    public AssistantThreadService(AssistantThreadRepository repository) {
        super(repository);
    }

    public AssistantThread getLatestThreadForUser(String userId) {
        return repository.findTopByUserIdOrderByCreationDateDesc(userId);
    }

}
