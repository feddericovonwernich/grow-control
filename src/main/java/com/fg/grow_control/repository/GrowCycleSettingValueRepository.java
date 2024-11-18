package com.fg.grow_control.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.GrowCycleSettingValue;

public interface GrowCycleSettingValueRepository extends JpaRepository<GrowCycleSettingValue, Long> {

    // Método que encuentra GrowCycleSetting por GrowCycleID y nombre de Setting
    Optional<GrowCycleSettingValue> findByGrowCycleIdAndSettingName(Long growCycleId, String settingName);
}
