package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameterType;
import com.fg.grow_control.entity.MeasurementDevice;
import com.fg.grow_control.repository.GrowingParameterTypeRepository;
import com.fg.grow_control.repository.MeasurementDeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GrowingParameterTypeService extends BasicService<GrowingParameterType, Long, GrowingParameterTypeRepository> {
    @Autowired
    public GrowingParameterTypeService(GrowingParameterTypeRepository repository) {
        super(repository);
    }

}
