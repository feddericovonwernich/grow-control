package com.fg.grow_control.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fg.grow_control.entity.GrowCycleSettingValue;
import com.fg.grow_control.repository.GrowCycleSettingValueRepository;

@Service
public class GrowCycleSettingValueService extends BasicService<GrowCycleSettingValue, Long, GrowCycleSettingValueRepository> {

    public GrowCycleSettingValueService(GrowCycleSettingValueRepository repository) {
        super(repository);
    }

    @Autowired
    private GrowCycleSettingValueRepository growCycleSettingRepository;

    // Método que busca por GrowCycleID y nombre de Setting
    public Optional<GrowCycleSettingValue> findByGrowCycleIdAndSettingName(Long growCycleId, String settingName) {
        return growCycleSettingRepository.findByGrowCycleIdAndSettingName(growCycleId, settingName);
    }

}
