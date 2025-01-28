package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import com.fg.grow_control.entity.schedule.RangeSchedule;
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

    @OneToOne
    @JoinColumn(name = "range_schedule_id", referencedColumnName = "id")
    RangeSchedule rangeSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grow_stage_type")
    @FieldDescription(description = "Reference to the grow stage type")
    @RequiredField
    private GrowStageType growStageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grow_cycle")
    @FieldDescription(description = "Reference to the grow cycle")
    @Reference
    private GrowCycle growCycle;

    @OneToMany(mappedBy = "growStage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Singular
    @Ignore
    private List<GrowingEvent> growingEvents;

    @OneToMany(mappedBy = "growStage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Singular
    @Ignore
    private List<MeasuredGrowingParameter> growingParameters;

}
