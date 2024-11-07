package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.entity.GrowingEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrowingEventRepository extends JpaRepository<GrowingEvent, Long> {
    Optional<GrowingEvent> findByGrowingEventTypeAndDescription(GrowingEventType growingEventType, String descriptionStringForTestUpdateEventScheduleGrowingEvent);
}

