package com.fg.grow_control.repository;

import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.DeviceTrigger;
import com.fg.grow_control.entity.MeasurementDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceTriggerRepository extends JpaRepository<DeviceTrigger, Long> {
    Optional<DeviceTrigger> findTopByTriggeredDeviceOrderByIdDesc(ActionDevice actionDevice);
}
