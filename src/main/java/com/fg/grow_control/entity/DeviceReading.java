package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // TODO This probably should not be a Long

    @ManyToOne
    @JoinColumn(name = "id_measurement_device")
    private MeasurementDevice measurementDevice;

    @Column(nullable = false)
    private Double reading;

}
