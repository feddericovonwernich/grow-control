package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // TODO This probably should not be a Long

    @ManyToOne
    @JoinColumn(name = "id_measurement_device")
    private MeasurementDevice measurementDevice;

    @Column(nullable = false)
    private Double reading;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Timestamp timestamp;

}
