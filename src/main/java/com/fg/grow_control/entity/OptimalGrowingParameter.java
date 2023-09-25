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
public class OptimalGrowingParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date_start;

    @Column(nullable = false)
    private Date date_end;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter")
    private GrowingParameter growingParameter;

    public OptimalGrowingParameter(Date date_start, Date date_end) {
        this.date_start = date_start;
        this.date_end = date_end;
    }
}
