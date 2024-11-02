package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.openia.parameter_classes.annotations.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass
public class GrowStageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Unique identifier for the GrowStageType")
    @ReferenceField
    private Long id;

    @Column(nullable = false, unique = true)
    @FieldDescription(description = "Name of the grow stage type")
    @RequiredField
    private String name;

}