package com.fg.grow_control.repository;

import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.entity.DeviceTrigger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceTriggerRepository extends JpaRepository<DeviceTrigger, Long> {
    Optional<DeviceTrigger> findTopByTriggeredDeviceOrderByIdDesc(ActionDevice actionDevice);
}
