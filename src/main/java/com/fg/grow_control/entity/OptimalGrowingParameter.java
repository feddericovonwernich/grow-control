package com.fg.grow_control.entity;

import com.fg.grow_control.entity.schedule.RangeSchedule;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass
public class OptimalGrowingParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReferenceField
    @FieldDescription(description = "Unique identifier for each OptimalGrowingParameter")
    private Long id;

    @OneToOne
    @JoinColumn(name = "range_schedule_id", referencedColumnName = "id")
    @RequiredField
    @FieldDescription(description = "The schedule for this GrowingParameter, marks when the this optimal value is desired.")
    private RangeSchedule rangeSchedule;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter")
    @RequiredField
    @Reference
    @FieldDescription(description = "Reference to the growing parameter")
    private MeasuredGrowingParameter growingParameter;

    @NotNull
    @Column(nullable = false)
    @RequiredField
    @FieldDescription(description = "This is the value we're aiming to have during the scheduled period of time.")
    private Double optimalValue;

}