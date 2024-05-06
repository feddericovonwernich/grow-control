package com.fg.grow_control.service.assistant.telegram;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistantThreadRepository extends JpaRepository<AssistantThread, Long> {
    AssistantThread findTopByUserIdOrderByCreationDateDesc(String userId);
}
