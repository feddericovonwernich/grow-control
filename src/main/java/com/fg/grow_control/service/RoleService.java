package com.fg.grow_control.service;

import org.springframework.stereotype.Service;

import com.fg.grow_control.entity.RoleEntity;
import com.fg.grow_control.repository.RoleRepository;

@Service
public class RoleService extends BasicService<RoleEntity, Long, RoleRepository> {

    public RoleService(RoleRepository repository) {
        super(repository);
    }
}
