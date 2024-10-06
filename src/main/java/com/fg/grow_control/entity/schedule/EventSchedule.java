package com.fg.grow_control.entity.schedule;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.SimpleTimestampConverter;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.FieldDescription;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.ParameterClass;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.ReferenceField;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.RequiredField;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass // Indicates this class is used for parameterization
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Primary key for EventSchedule entity") // Describing the ID field
    @ReferenceField // Marks this field as a reference field
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false)
    @FieldDescription(description = "Type of schedule, detailing the nature of the schedule") // Detailed description
    @RequiredField // Marks this field as required
    private ScheduleType type;

    @Convert(converter = SimpleTimestampConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "date")
    @FieldDescription(description = "The specific date and time when the event is scheduled. REQUIRED if the type is FIXED. MUST BE NULL if type is RELATIVE.")
    // TODO Test that this is not set for Relative type.
    private SimpleTimestamp date;

    @Enumerated(EnumType.STRING)
    @Column(name = "units")
    @FieldDescription(description = "Temporal units used for scheduling. REQUIRED if the type is RELATIVE. MUST BE NULL if type is FIXED.") // Explanation of the units
    // TODO Test that this is not set for Fixed type.
    private ChronoUnit units;

    @Column(name = "unit_value")
    @FieldDescription(description = "Numeric value associated with the temporal units, defining the schedule's duration or recurrence. REQUIRED if the type is RELATIVE. MUST BE NULL if type is FIXED.")
    // TODO Test that this is not set for Fixed type.
    private Double unitValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    @RequiredField
    @FieldDescription(description = "Specifies the direction in relation to a reference point. For FIXED ScheduleType, only valid value is NONE. For RELATIVE ScheduleType, only valid values are BEFORE, AFTER")
    private Direction direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference")
    @RequiredField
    @FieldDescription(description = "Reference point for schedule. For FIXED ScheduleType, only valid value is NONE. For RELATIVE ScheduleType, only valid values are BEGINNING, END.") // Clarifying the reference
    private OffsetReference reference;

}
