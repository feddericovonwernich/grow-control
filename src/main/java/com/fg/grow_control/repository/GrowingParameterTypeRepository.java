package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingParameterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrowingParameterTypeRepository extends JpaRepository<GrowingParameterType, Long> {
    Optional<GrowingParameterType> findByName(String name);

}
