package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GrowCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date_start;

    @Column(nullable = false)
    private Date date_end;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrowStage> growStages;

    public GrowCycle(String description, Date date_start, Date date_end) {
        this.description = description;
        this.date_start = date_start;
        this.date_end = date_end;
    }
}
