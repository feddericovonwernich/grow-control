package com.fg.grow_control.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GrowingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_grow_stage")
    private GrowStage growStage;

    @ManyToOne
    @JoinColumn(name = "id_growing_event_type")
    private GrowingEventType growingeventtype;

    public GrowingEvent(String description, Date date) {
        this.description = description;
        this.date = date;
    }
}
