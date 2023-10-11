package com.fg.grow_control.repository;

import com.fg.grow_control.entity.MeasurementDevice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementDeviceRepository extends JpaRepository<MeasurementDevice, Long> {
}
