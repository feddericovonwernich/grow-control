package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowCycle;
import com.fg.grow_control.repository.GrowCycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowCycleService extends BasicService<GrowCycle, Long, GrowCycleRepository>{
    @Autowired
    public GrowCycleService (GrowCycleRepository repository) { super(repository);}

}
