package com.password926.agijagi.milestone.infrastructure;

import com.password926.agijagi.milestone.domain.MilestoneStateDetail;

import java.util.List;

public interface MilestoneCustomRepository {

    List<MilestoneStateDetail> findMilestoneDetails(long childId, int month);
}
