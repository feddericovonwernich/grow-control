package com.fg.grow_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.RoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
