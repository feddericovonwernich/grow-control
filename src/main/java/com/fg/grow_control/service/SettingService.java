package com.fg.grow_control.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fg.grow_control.entity.Setting;
import com.fg.grow_control.repository.SettingRepository;
import java.util.Optional;

@Service
public class SettingService extends BasicService<Setting, Long, SettingRepository> {

    public SettingService(SettingRepository repository) {
        super(repository);
    }

    @Autowired
    private SettingRepository deviceConfigurationRepository;

    public Optional<Setting> findByName(String name) {
        return deviceConfigurationRepository.findByName(name);
    }
}
