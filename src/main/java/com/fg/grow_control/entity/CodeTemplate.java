package com.fg.grow_control.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CodeTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Para almacenar texto grande
    private String template;

    @ElementCollection
    private List<String> templateVars;

    @ManyToOne
    private DeviceType deviceType;
}