package com.fg.grow_control.repository;

import com.fg.grow_control.entity.MeasurementDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasurementDeviceRepository extends JpaRepository<MeasurementDevice, Long> {
    Optional<MeasurementDevice> findByGrowingParameterTypeName(String typeName);
}
