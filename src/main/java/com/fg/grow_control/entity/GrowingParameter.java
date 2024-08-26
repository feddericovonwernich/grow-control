package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass
public class GrowingParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RequiredField
    @FieldDescription(description = "Unique identifier for GrowingParameter")
    @ReferenceField
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_grow_stage")
    @RequiredField
    @Reference
    @FieldDescription(description = "Reference to the grow stage associated with this parameter")
    private GrowStage growStage;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter_type")
    @RequiredField
    @FieldDescription(description = "Type of the growing parameter")
    private GrowingParameterType growingParameterType;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    @Ignore
    private List<OptimalGrowingParameter> optimalGrowingParameters;

    @OneToOne(mappedBy = "growingParameter")
    @Ignore
    private MeasurementDevice measurementDevice;
}
