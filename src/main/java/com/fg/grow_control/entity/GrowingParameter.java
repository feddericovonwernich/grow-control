package com.fg.grow_control.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GrowingParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long value;

    @ManyToOne
    @JoinColumn(name = "id_grow_stage")
    private GrowStage growStage;

    @ManyToOne
    @JoinColumn(name = "id_growing_parameter_type")
    private GrowingParameterType growingParameterType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrowingParameterValueTime> growingParameterValueTimes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptimalGrowingParameter> optimalGrowingParameters;

    public GrowingParameter(Long value) {
        this.value = value;
    }
}
