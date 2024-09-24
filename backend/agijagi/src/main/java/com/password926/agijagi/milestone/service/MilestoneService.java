package com.password926.agijagi.milestone.service;

import com.password926.agijagi.milestone.domain.MilestoneStateContent;
import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import com.password926.agijagi.milestone.domain.MilestoneStateDetailReader;
import com.password926.agijagi.milestone.domain.MilestoneStateUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneStateDetailReader milestoneStateDetailReader;
    private final MilestoneStateUpdater milestoneStateUpdater;

    public List<MilestoneStateDetail> readMilestone(long memberId, long childId, int month) {
        return milestoneStateDetailReader.read(memberId, childId, month);
    }

    public void updateMilestone(
            long memberId,
            long childId,
            List<MilestoneStateContent> milestoneStateContents
    ) {
        milestoneStateUpdater.update(memberId, childId, milestoneStateContents);
    }
}
