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
public class GrowingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReferenceField
    @FieldDescription(description = "Identity field for GrowingEvent")
    private Long id;

    @Column(nullable = false)
    @FieldDescription(description = "Description of the growing event")
    @RequiredField
    private String description;

    @Convert(converter = SimpleTimestampConverter.class)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @FieldDescription(description = "Timestamp of the growing event occurrence")
    @RequiredField
    private SimpleTimestamp date;

    @ManyToOne
    @JoinColumn(name = "id_grow_stage")
    @Reference
    private GrowStage growStage;

    @ManyToOne
    @JoinColumn(name = "id_growing_event_type")
    @FieldDescription(description = "Reference to the growing event type")
    @RequiredField
    private GrowingEventType growingEventType;

}