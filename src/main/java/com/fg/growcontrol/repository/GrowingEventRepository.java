package com.fg.growcontrol.repository;

import com.fg.growcontrol.entity.GrowingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowingEventRepository extends JpaRepository<GrowingEvent, Long> {

}
