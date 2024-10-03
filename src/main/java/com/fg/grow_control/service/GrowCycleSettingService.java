package com.fg.grow_control.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fg.grow_control.entity.GrowCycleSetting;
import com.fg.grow_control.repository.GrowCycleSettingRepository;

@Service
public class GrowCycleSettingService extends BasicService<GrowCycleSetting, Long, GrowCycleSettingRepository> {

    public GrowCycleSettingService(GrowCycleSettingRepository repository) {
        super(repository);
    }

    @Autowired
    private GrowCycleSettingRepository growCycleSettingRepository;

    // Método que busca por GrowCycleID y nombre de Setting
    public Optional<GrowCycleSetting> findByGrowCycleIdAndSettingName(Long growCycleId, String settingName) {
        return growCycleSettingRepository.findByGrowCycleIdAndSettingName(growCycleId, settingName);
    }

}
