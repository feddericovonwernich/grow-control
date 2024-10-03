package com.fg.grow_control.dto;

import java.util.List;
import com.fg.grow_control.entity.DeviceType;
import lombok.*;

@Getter
@Setter
@Builder
public class DeviceConfigurationRequestDTO {

    private Long growCycleId;
    
    private List<DeviceType> devices;
}
