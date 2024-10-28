package com.fg.grow_control.repository;

import com.fg.grow_control.entity.GrowCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrowCycleRepository extends JpaRepository<GrowCycle, Long> {
}
