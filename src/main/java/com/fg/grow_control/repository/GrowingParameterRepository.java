package com.fg.grow_control.repository;

import com.fg.grow_control.entity.MeasuredGrowingParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowingParameterRepository extends JpaRepository<MeasuredGrowingParameter, Long> {
}
