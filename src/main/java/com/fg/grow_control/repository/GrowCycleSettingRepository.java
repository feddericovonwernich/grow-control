package com.fg.grow_control.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.GrowCycleSetting;

public interface GrowCycleSettingRepository extends JpaRepository<GrowCycleSetting, Long> {

    // Método que encuentra GrowCycleSetting por GrowCycleID y nombre de Setting
    Optional<GrowCycleSetting> findByGrowCycleIdAndSettingName(Long growCycleId, String settingName);
}
