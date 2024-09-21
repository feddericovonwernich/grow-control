package com.fg.grow_control.entity;

import com.fg.grow_control.entity.schedule.RangeSchedule;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class GrowCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Simple identification field")
    @ReferenceField
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @FieldDescription(description = "Detailed description of the grow cycle")
    @RequiredField
    private String description;

    @FieldDescription(description = "The schedule for this GrowCycle, marks when the cycle will start and end.")
    @OneToOne
    @JoinColumn(name = "range_schedule_id", referencedColumnName = "id")
    @RequiredField
    RangeSchedule rangeSchedule;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    @Ignore
    private List<GrowStage> growStages;

}
