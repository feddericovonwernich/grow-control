package com.fg.grow_control.service.assistant.telegram;

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
public class AssistantThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String threadId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationDate;

}
