package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GrowCycleSettingValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "grow_cycle_id", nullable = false)
    private GrowCycle growCycle;

    @ManyToOne
    @JoinColumn(name = "setting_id", nullable = false)
    private Setting setting;
}