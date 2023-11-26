package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowingParameterService extends BasicService<GrowingParameter, Long, GrowingParameterRepository> {
    @Autowired
    public GrowingParameterService(GrowingParameterRepository repository) {
        super(repository);
    }

}
