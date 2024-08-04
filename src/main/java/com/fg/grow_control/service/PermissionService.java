package com.fg.grow_control.service;

import org.springframework.stereotype.Service;
import com.fg.grow_control.entity.PermissionEntity;
import com.fg.grow_control.repository.PermissionRepository;

@Service
public class PermissionService extends BasicService<PermissionEntity, Long, PermissionRepository>{

    public PermissionService(PermissionRepository repository) {
        super(repository);
    }

}
