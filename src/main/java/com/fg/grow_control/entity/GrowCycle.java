package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)  // Indica que es un campo de fecha y hora
    private Timestamp date_start;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date_end;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrowStage> growStages;

    public GrowCycle(String description, Timestamp date_start, Timestamp date_end) {
        this.description = description;
        this.date_start = date_start;
        this.date_end = date_end;
    }
}
