package com.fg.grow_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fg.grow_control.entity.CodeTemplate;
import com.fg.grow_control.entity.DeviceType;
import java.util.Optional;

public interface CodeTemplateRepository extends JpaRepository<CodeTemplate, Long> {

    Optional<CodeTemplate> findByDeviceType(DeviceType deviceType);
}
