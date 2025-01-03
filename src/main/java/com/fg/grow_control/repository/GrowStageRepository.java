package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowStageRepository extends JpaRepository<GrowStage, Long> {
}
