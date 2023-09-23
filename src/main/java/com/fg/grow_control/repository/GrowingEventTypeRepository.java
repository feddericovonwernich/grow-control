package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingEventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowingEventTypeRepository extends JpaRepository<GrowingEventType, Long> {
}
