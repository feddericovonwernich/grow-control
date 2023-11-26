package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.repository.GrowingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowingEventService extends BasicService<GrowingEvent, Long, GrowingEventRepository> {
    @Autowired
    public GrowingEventService(GrowingEventRepository repository) {
        super(repository);
    }
}
