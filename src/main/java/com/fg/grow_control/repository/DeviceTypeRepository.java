package com.fg.grow_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
}
