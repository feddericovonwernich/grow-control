package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowingParameterValueTime {

    @Id
    @GeneratedValue (strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long value;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter")
    private GrowingParameter growingParameter;

}
