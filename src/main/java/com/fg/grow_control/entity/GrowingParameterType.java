package com.fg.grow_control.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ParameterClass
public class GrowingParameterType {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @FieldDescription(description = "Unique identifier for GrowingParameterType")
    @ReferenceField
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @FieldDescription(description = "Name of the GrowingParameterType")
    @RequiredField
    private String name;

}

