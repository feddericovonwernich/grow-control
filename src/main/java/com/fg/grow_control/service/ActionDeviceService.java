package com.fg.grow_control.service;

import com.fg.grow_control.entity.ActionDevice;
import com.fg.grow_control.repository.ActionDeviceRepository;
import org.springframework.stereotype.Component;

@Component
public class ActionDeviceService extends BasicService<ActionDevice, Long, ActionDeviceRepository> {
    public ActionDeviceService(ActionDeviceRepository repository) {
        super(repository);
    }
}
