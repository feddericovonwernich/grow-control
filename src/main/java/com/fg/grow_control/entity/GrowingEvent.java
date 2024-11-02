package com.fg.grow_control.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fg.grow_control.entity.schedule.EventSchedule;
import io.github.feddericovonwernich.spring_ai.function_calling_service.openia.parameter_classes.annotations.*;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;

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

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "event_schedule_id", referencedColumnName = "id")
    @JsonProperty("eventSchedule")
    @RequiredField
    @FieldDescription(description = "The schedule for this GrowingEvent, marks when this event should happen.")
    EventSchedule eventSchedule;

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