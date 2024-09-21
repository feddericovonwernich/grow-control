package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowStageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrowStageTypeRepository extends JpaRepository<GrowStageType, Long> {
    Optional<GrowStageType> findByName(String stringForTestRegisterReading);

}
