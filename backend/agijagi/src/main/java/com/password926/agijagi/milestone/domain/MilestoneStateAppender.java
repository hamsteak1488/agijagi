package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.milestone.infrastructure.MilestoneStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MilestoneStateAppender {

    private final MilestoneReader milestoneReader;
    private final MilestoneStateRepository milestoneStateRepository;

    // TODO: saveAll로 수정, milestone 조회 안하도록 수정
    public void append(Child child) {
        List<Milestone> milestones = milestoneReader.readAll();
        for (Milestone milestone : milestones) {
            milestoneStateRepository.save(MilestoneState.builder()
                    .child(child)
                    .milestone(milestone)
                    .currentAmount(0)
                    .isAchieved(false)
                    .build());
        }
    }
}
