package com.fg.grow_control.service;

import com.fg.grow_control.entity.GrowingParameter;
import com.fg.grow_control.repository.GrowingParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrowingParameterService extends BasicService<GrowingParameter, Long, GrowingParameterRepository> {

    @Autowired
    private GrowingParameterTypeService growingParameterTypeService;

    @Autowired
    private MeasurementDeviceService measurementDeviceService;

    @Autowired
    public GrowingParameterService(GrowingParameterRepository repository) {
        super(repository);
    }

    @Override
    public GrowingParameter createOrUpdate(GrowingParameter object) {

        if(object.getGrowingParameterType() != null && object.getGrowingParameterType().getId() == null) {
            growingParameterTypeService.createOrUpdate(object.getGrowingParameterType());
        }
        if (object.getMeasurementDevice() != null && object.getMeasurementDevice().getId() == null){
            measurementDeviceService.createOrUpdate(object.getMeasurementDevice());
        }

        return super.createOrUpdate(object);
    }
}
