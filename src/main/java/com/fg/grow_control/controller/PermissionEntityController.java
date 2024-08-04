package com.fg.grow_control.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fg.grow_control.entity.PermissionEntity;
import com.fg.grow_control.repository.PermissionRepository;
import com.fg.grow_control.service.PermissionService;

@RestController
@RequestMapping("/permissions")
@PreAuthorize("permitAll()")
public class PermissionEntityController extends BasicController <PermissionEntity, Long, PermissionRepository, PermissionService> {

    public PermissionEntityController(PermissionService service) {
        super(service);
    }

}
