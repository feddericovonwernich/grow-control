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
public class GrowStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Primary key for GrowStage entity")
    @ReferenceField
    private Long id;

    @Column(nullable = false)
    @FieldDescription(description = "Type of duration unit, e.g. 'Hour', 'Day'")
    @RequiredField
    private String durationUnit;

    @Column(nullable = false)
    @FieldDescription(description = "Value of the duration in the specified unit")
    @RequiredField
    private Long durationValue;

    @ManyToOne
    @JoinColumn(name = "id_grow_stage_type")
    @FieldDescription(description = "Reference to the grow stage type")
    @RequiredField
    private GrowStageType growStageType;

    @ManyToOne
    @JoinColumn(name = "id_grow_cycle")
    @FieldDescription(description = "Reference to the grow cycle")
    @Reference
    private GrowCycle growCycle;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    @Ignore
    private List<GrowingEvent> growingEvents;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    @Ignore
    private List<GrowingParameter> growingParameters;

}
