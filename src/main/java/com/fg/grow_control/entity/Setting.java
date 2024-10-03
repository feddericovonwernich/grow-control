package com.fg.grow_control.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import io.github.feddericovonwernich.spring_ai.function_calling_service.annotations.Ignore;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "setting")
    @Singular
    @Ignore
    private List<GrowCycleSetting> growCycleSettings;
}
