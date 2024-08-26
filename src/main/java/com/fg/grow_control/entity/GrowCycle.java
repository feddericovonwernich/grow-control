package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(nullable = false)
    @FieldDescription(description = "Detailed description of the grow cycle")
    @RequiredField
    private String description;

    @Convert(converter = SimpleTimestampConverter.class)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @FieldDescription(description = "The start date and time of the grow cycle")
    @RequiredField
    private SimpleTimestamp date_start;

    @Convert(converter = SimpleTimestampConverter.class)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @FieldDescription(description = "The end date and time of the grow cycle")
    @RequiredField
    private SimpleTimestamp date_end;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    @Ignore
    private List<GrowStage> growStages;

}
