package com.fg.grow_control.repository;

import com.fg.grow_control.entity.DeviceReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceReadingRepository extends JpaRepository<DeviceReading, Long> {
}
