package com.fg.grow_control.entity;

import com.fg.grow_control.service.MeasurementDeviceService;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter_type")
    private GrowingParameterType growingParameterType;



    @OneToOne
    private GrowingParameter growingParameter;
}
