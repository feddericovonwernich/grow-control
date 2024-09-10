package com.fg.grow_control.service;

import org.springframework.stereotype.Service;
import com.fg.grow_control.entity.UserEntity;
import com.fg.grow_control.repository.UserRepository;

@Service
public class UserService extends BasicService<UserEntity, Long, UserRepository> {

    public UserService(UserRepository repository) {
        super(repository);
    }

}
