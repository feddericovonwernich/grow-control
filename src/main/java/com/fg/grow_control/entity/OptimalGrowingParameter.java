package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OptimalGrowingParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp date_start;

    @Column(nullable = false)
    private Timestamp date_end;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter")
    private GrowingParameter growingParameter;

}
