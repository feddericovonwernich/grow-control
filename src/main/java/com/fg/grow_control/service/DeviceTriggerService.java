package com.fg.grow_control.service;

import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.entity.DeviceReading;
import com.fg.grow_control.entity.DeviceTrigger;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.DeviceTriggerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DeviceTriggerService extends BasicService<DeviceTrigger, Long, DeviceTriggerRepository> {

    public DeviceTriggerService(DeviceTriggerRepository repository) {
        super(repository);
    }

    public DeviceTrigger getLastTriggerForDevice(ActionDevice actionDevice) {
        // Utilizing custom repository method to find the last DeviceTrigger for a given ActionDevice
        return repository.findTopByTriggeredDeviceOrderByIdDesc(actionDevice)
                .orElse(null);
    }
}
