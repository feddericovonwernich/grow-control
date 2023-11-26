package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String durationUnit;

    @Column(nullable = false)
    private Long durationValue;

    @ManyToOne
    @JoinColumn(name = "id_grow_stage_type")
    private GrowStageType growStageType;

    @ManyToOne
    @JoinColumn(name = "id_grow_cycle")
    private GrowCycle growCycle;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrowingEvent> growingEvent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrowingParameter> growingParameter;

    public GrowStage(String durationUnit, Long durationValue) {
        this.durationUnit = durationUnit;
        this.durationValue = durationValue;
    }
}
