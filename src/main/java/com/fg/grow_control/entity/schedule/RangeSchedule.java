package com.fg.grow_control.entity.schedule;

import com.fg.grow_control.entity.SimpleTimestamp;
import com.fg.grow_control.entity.SimpleTimestampConverter;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
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
@ParameterClass
public class RangeSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Primary key for RangeSchedule entity")
    @ReferenceField
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false)
    @FieldDescription(description = "Type of schedule")
    @RequiredField
    private ScheduleType type;

    @Convert(converter = SimpleTimestampConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "start_date")
    @FieldDescription(description = "Start date of the schedule. REQUIRED if type is FIXED. MUST BE NULL if type is RELATIVE.")
    private SimpleTimestamp start;

    @Convert(converter = SimpleTimestampConverter.class)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "end_date")
    @FieldDescription(description = "End date of the schedule. REQUIRED if type is FIXED. MUST BE NULL if type is RELATIVE.")
    private SimpleTimestamp end;

    @Enumerated(EnumType.STRING)
    @Column(name = "units")
    @FieldDescription(description = "Chrono units for the schedule duration. REQUIRED if type is RELATIVE. MUST BE NULL if type is FIXED.")
    private ChronoUnit units;

    @Column(name = "unit_value")
    @FieldDescription(description = "Value associated with the ChronoUnits. REQUIRED if type is RELATIVE. MUST BE NULL if type is FIXED.")
    private Double unitValue;

    // TODO Is this missing Direction, and OffsetReference? Need to think about this.
    //  Start date is relative to something. End date is calculated from unit and unitValue.

}