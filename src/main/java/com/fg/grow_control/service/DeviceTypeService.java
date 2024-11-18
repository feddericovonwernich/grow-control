package com.fg.grow_control.service;

import org.springframework.stereotype.Service;

import com.fg.grow_control.entity.DeviceType;
import com.fg.grow_control.repository.DeviceTypeRepository;

@Service
public class DeviceTypeService extends BasicService<DeviceType, Long, DeviceTypeRepository> {

    public DeviceTypeService(DeviceTypeRepository repository) {
        super(repository);
    }
}
