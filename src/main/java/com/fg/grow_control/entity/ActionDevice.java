package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinTable(name = "measurement_dev_to_action_dev",
            joinColumns =
                    { @JoinColumn(name = "action_device_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "measurement_device_id", referencedColumnName = "id") })
    MeasurementDevice watchedMeasurement;

    @Column(nullable = false)
    Integer activationThreshold;

    @Column(nullable = false)
    Integer deactivationThreshold;

}
