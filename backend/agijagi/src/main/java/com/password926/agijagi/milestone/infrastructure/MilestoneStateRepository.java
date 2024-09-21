package com.password926.agijagi.milestone.infrastructure;

import com.password926.agijagi.milestone.domain.MilestoneState;
import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MilestoneStateRepository extends Repository<MilestoneState, Long> {

    @Query("SELECT ms.id, m.title, m.content, m.requiredAmount, ms.currentAmount, ms.date " +
            "FROM Milestone m " +
            "JOIN MilestoneState ms ON m.id = ms.milestone.id " +
            "WHERE ms.child.id = :childId AND m.month = :month")
    List<MilestoneStateDetail> findMilestoneDetails(long childId, int month);
}
