package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.entity.GrowingEventType;
import com.fg.grow_control.repository.GrowingEventRepository;
import com.fg.grow_control.repository.GrowingEventTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrowingEventTypeService extends BasicService<GrowingEventType, Long, GrowingEventTypeRepository> {

    @Autowired
    public GrowingEventTypeService(GrowingEventTypeRepository repository) {
        super(repository);
    }

}
