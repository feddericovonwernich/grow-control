package com.fg.grow_control.repository;

import com.fg.grow_control.entity.MeasurementDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasurementDeviceRepository extends JpaRepository<MeasurementDevice, Long> {

    Optional<MeasurementDevice> findByGrowingParameterTypeName(String typeName);
}
