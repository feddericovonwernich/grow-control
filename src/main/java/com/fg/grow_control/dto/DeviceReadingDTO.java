package com.fg.grow_control.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class DeviceReadingDTO {

    private Long deviceId;

    private Double measurementValue;

}
