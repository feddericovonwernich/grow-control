package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.*;
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
public class DeviceTrigger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReferenceField
    @FieldDescription(description = "Unique identifier for the DeviceTrigger")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_action_device")
    @RequiredField
    @FieldDescription(description = "Link to the action device that is triggered")
    private ActionDevice triggeredDevice;

    @Column(nullable = false)
    @RequiredField
    @FieldDescription(description = "Value at which the device trigger is activated")
    private Long triggerValue;

    @Convert(converter = SimpleTimestampConverter.class)
    @Column
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @FieldDescription(description = "Timestamp when the triggering occurs")
    @RequiredField
    private SimpleTimestamp triggerTime;

    @Column
    @RequiredField
    @FieldDescription(description = "Whether the trigger is completed or not")
    private boolean completed;
}