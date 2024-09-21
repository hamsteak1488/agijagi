package com.password926.agijagi.milestone.service;

import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import com.password926.agijagi.milestone.domain.MilestoneStateDetailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneStateDetailReader milestoneStateDetailReader;

    public List<MilestoneStateDetail> readMilestone(long memberId, long childId, int month) {
        return milestoneStateDetailReader.read(memberId, childId, month);
    }
}
