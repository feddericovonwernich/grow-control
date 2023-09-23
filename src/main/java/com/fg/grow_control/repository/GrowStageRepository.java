package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowStageRepository extends JpaRepository<GrowStage, Long> {
}
