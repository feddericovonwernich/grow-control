package com.fg.grow_control.repository;

import com.fg.grow_control.entity.OptimalGrowingParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptimalGrowingParameterRepository extends JpaRepository<OptimalGrowingParameter, Long> {
}
