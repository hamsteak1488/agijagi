package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.milestone.infrastructure.MilestoneStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MilestoneStateDetailReader {

    private final ChildValidator childValidator;
    private final MilestoneStateRepository milestoneStateRepository;

    public List<MilestoneStateDetail> read(long memberId, long childId, int month) {
        childValidator.validateWriterRole(memberId, childId);
        return milestoneStateRepository.findMilestoneDetails(childId, month);
    }
}
