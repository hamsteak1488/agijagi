package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.milestone.infrastructure.MilestoneCustomImplRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MilestoneStateDetailReader {

    private final ChildValidator childValidator;
    private final MilestoneCustomImplRepository milestoneCustomImplRepository;

    public List<MilestoneStateDetail> read(long memberId, long childId, int month) {
        childValidator.validateReadAuthority(memberId, childId);
        return milestoneCustomImplRepository.findMilestoneDetails(childId, month);
    }
}
