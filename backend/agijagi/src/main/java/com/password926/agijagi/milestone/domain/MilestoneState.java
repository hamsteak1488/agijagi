package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.Child;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MilestoneState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id", nullable = false)
    private Milestone milestone;

    @Column(nullable = false)
    private int currentAmount;

    private LocalDate date;

    private boolean isAchieved;

    public void update(int currentAmount) {
        if (currentAmount > milestone.getRequiredAmount()) {
            currentAmount = milestone.getRequiredAmount();
        }
        if (currentAmount < 0) {
            currentAmount = 0;
        }
        this.currentAmount = currentAmount;
    }
}
