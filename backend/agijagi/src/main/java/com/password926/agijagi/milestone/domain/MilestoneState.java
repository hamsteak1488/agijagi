package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.Child;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class MilestoneState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "milestone_id", nullable = false)
    private Milestone milestone;

    @Column(nullable = false)
    private int currentAmount;

    private LocalDate date;

    private boolean isAchieved;
}
