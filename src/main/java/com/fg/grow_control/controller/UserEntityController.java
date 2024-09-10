package com.fg.grow_control.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fg.grow_control.entity.UserEntity;
import com.fg.grow_control.repository.UserRepository;
import com.fg.grow_control.service.UserService;

@RestController
@RequestMapping("/users")
@PreAuthorize("permitAll()")
public class UserEntityController extends BasicController<UserEntity, Long, UserRepository, UserService> {

    public UserEntityController(UserService service) {
        super(service);
    }
}
