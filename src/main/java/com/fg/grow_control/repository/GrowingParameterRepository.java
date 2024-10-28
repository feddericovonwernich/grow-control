package com.fg.grow_control.repository;

import com.fg.grow_control.entity.MeasuredGrowingParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowingParameterRepository extends JpaRepository<MeasuredGrowingParameter, Long> {
}
