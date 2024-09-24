package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MilestoneStateUpdater {

    private final ChildValidator childValidator;
    private final MilestoneStateReader milestoneStateReader;

    @Transactional
    public void update(
            long memberId,
            long childId,
            List<MilestoneStateContent> milestoneStateContents
    ) {
        childValidator.validateWriterRole(memberId, childId);
        milestoneStateContents.forEach(msc ->
                milestoneStateReader.read(msc.getId()).update(msc.getCurrentAmount())
        );
    }
}
