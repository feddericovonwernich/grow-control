package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.entity.GrowingEventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrowingEventRepository extends JpaRepository<GrowingEvent, Long> {
    Optional<GrowingEvent> findByGrowingEventTypeAndDescription(GrowingEventType growingEventType, String descriptionStringForTestUpdateEventScheduleGrowingEvent);
}

