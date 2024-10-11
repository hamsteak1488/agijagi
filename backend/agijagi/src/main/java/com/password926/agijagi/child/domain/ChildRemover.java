package com.password926.agijagi.child.domain;

import com.password926.agijagi.milestone.domain.MilestoneStateRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChildRemover {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final MemberChildRemover memberChildRemover;
    private final MilestoneStateRemover milestoneStateRemover;

    @Transactional
    public void remove(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        Child child = childReader.read(childId);
        memberChildRemover.removeAllRelation(childId);
        milestoneStateRemover.remove(child);
        child.remove();
    }
}
