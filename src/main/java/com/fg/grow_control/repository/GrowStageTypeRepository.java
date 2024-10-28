package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowStageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrowStageTypeRepository extends JpaRepository<GrowStageType, Long> {
    Optional<GrowStageType> findByName(String stringForTestRegisterReading);

}
