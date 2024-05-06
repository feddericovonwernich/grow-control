package com.fg.grow_control.repository;

import com.fg.grow_control.entity.ActionDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ActionDeviceRepository extends JpaRepository<ActionDevice, Long> {
}
