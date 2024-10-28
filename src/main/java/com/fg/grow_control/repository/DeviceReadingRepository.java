package com.fg.grow_control.repository;

import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.MeasurementDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceReadingRepository extends JpaRepository<DeviceReading, Long> {

    // Implementing method to find the top DeviceReading entry for a MeasurementDevice ordered by ID in descending order
    // This ensures we retrieve the most recent reading for a particular device.
    Optional<DeviceReading> findTopByMeasurementDeviceOrderByIdDesc(MeasurementDevice measurementDevice);

}
