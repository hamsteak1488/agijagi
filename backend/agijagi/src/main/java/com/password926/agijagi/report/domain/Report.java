package com.password926.agijagi.report.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long childId;

    private int month;

    @Column(nullable = false)
    private String content;

    private int growthDegree;

    private int maxDegree;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
