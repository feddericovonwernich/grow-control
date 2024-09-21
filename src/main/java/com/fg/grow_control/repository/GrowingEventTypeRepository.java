package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingEventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrowingEventTypeRepository extends JpaRepository<GrowingEventType, Long> {
    Optional<GrowingEventType> findByName(String name);
}
