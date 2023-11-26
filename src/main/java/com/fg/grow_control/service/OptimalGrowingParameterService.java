package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowStage;
import com.fg.grow_control.entity.GrowingEvent;
import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.entity.OptimalGrowingParameter;
import com.fg.grow_control.repository.GrowStageRepository;
import com.fg.grow_control.repository.OptimalGrowingParameterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OptimalGrowingParameterService extends BasicService<OptimalGrowingParameter, Long, OptimalGrowingParameterRepository>  {
    @Autowired
    public OptimalGrowingParameterService(OptimalGrowingParameterRepository repository) {
        super(repository);
    }

    @Autowired
    private GrowingParameterService growingParameterService;

    @Override
    public OptimalGrowingParameter createOrUpdate(OptimalGrowingParameter optimalGrowingParameter) {

        if (optimalGrowingParameter.getGrowingParameter().getId() == null){
            growingParameterService.createOrUpdate(optimalGrowingParameter.getGrowingParameter());
        }

        return super.createOrUpdate(optimalGrowingParameter);
    }
}
