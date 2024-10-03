package com.fg.grow_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.Setting;
import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {

    Optional<Setting> findByName(String name);
}
