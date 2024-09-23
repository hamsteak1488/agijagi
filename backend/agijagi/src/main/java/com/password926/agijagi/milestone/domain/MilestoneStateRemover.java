package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.milestone.infrastructure.MilestoneStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MilestoneStateRemover {

    private final MilestoneStateRepository milestoneStateRepository;

    public void remove(Child child) {
        milestoneStateRepository.removeAllByChild(child);
    }
}
