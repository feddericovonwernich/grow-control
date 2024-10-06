package com.fg.grow_control.entity;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
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
public class GrowingEventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FieldDescription(description = "Identification field for GrowingEventType")
    @ReferenceField
    private Long id;

    @Column(nullable = false, unique = true)
    @RequiredField  // Assuming the field is required
    @FieldDescription(description = "Name of the growing event type")
    private String name;

}
