package com.fg.grow_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.ArduinoCodeTemplate;
import com.fg.grow_control.entity.DeviceType;
import java.util.Optional;

public interface ArduinoCodeTemplateRepository extends JpaRepository<ArduinoCodeTemplate, Long> {

    Optional<ArduinoCodeTemplate> findByDeviceType(DeviceType deviceType);
}
