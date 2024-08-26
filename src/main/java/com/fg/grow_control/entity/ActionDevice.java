package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass
public class ActionDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Unique identifier for the ActionDevice entity")
    @ReferenceField
    private Long id;

    @OneToOne
    @JoinTable(name = "measurement_dev_to_action_dev",
            joinColumns =
                    {@JoinColumn(name = "action_device_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "measurement_device_id", referencedColumnName = "id")})
    @FieldDescription(description = "Measurement device associated with the action device")
    @Reference
    MeasurementDevice watchedMeasurement;

    @Column(nullable = false)
    @FieldDescription(description = "Threshold for activating the device")
    @RequiredField
    Integer activationThreshold;

    @Column(nullable = false)
    @FieldDescription(description = "Threshold for deactivating the device")
    @RequiredField
    Integer deactivationThreshold;
}
