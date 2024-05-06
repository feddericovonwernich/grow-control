package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowingEventRepository extends JpaRepository<GrowingEvent, Long> {
}
