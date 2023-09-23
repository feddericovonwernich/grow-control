package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowingParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowingParameterRepository extends JpaRepository<GrowingParameter, Long> {
}
