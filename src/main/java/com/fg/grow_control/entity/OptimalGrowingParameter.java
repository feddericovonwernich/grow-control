package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Convert(converter = SimpleTimestampConverter.class)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @RequiredField
    @FieldDescription(description = "Start timestamp of the optimal growing parameter")
    private SimpleTimestamp date_start;

    @Convert(converter = SimpleTimestampConverter.class)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @RequiredField
    @FieldDescription(description = "End timestamp of the optimal growing parameter")
    private SimpleTimestamp date_end;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter")
    @RequiredField
    @Reference
    @FieldDescription(description = "Reference to the growing parameter")
    private GrowingParameter growingParameter;

}