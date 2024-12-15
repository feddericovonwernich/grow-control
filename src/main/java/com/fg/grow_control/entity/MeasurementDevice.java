package com.fg.grow_control.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass
public class MeasurementDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReferenceField
    @FieldDescription(description = "Unique identifier for MeasurementDevice")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_growing_parameter_type")
    @RequiredField
    @FieldDescription(description = "Reference to the growing parameter type")
    private GrowingParameterType growingParameterType;

    @OneToOne
    @FieldDescription(description = "Associated growing parameter")
    @Reference
    @JsonIgnore
    private MeasuredGrowingParameter growingParameter;
}