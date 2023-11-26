package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameterValueTime;
import com.fg.grow_control.repository.GrowingParameterValueTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowingParameterValueTimeService extends BasicService<GrowingParameterValueTime, Long, GrowingParameterValueTimeRepository> {
    @Autowired
    public GrowingParameterValueTimeService(GrowingParameterValueTimeRepository repository) {
        super(repository);
    }
}
