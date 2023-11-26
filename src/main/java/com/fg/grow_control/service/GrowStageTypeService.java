package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowStageType;
import com.fg.grow_control.repository.GrowStageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowStageTypeService extends BasicService<GrowStageType, Long, GrowStageTypeRepository> {
    @Autowired
    public GrowStageTypeService(GrowStageTypeRepository repository) {
        super(repository);
    }
}
