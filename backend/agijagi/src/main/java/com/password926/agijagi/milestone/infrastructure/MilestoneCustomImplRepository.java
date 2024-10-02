package com.password926.agijagi.milestone.infrastructure;

import static com.password926.agijagi.milestone.domain.QMilestoneState.milestoneState;
import static com.password926.agijagi.milestone.domain.QMilestone.milestone;

import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MilestoneCustomImplRepository implements MilestoneCustomRepository{

    private final JPAQueryFactory query;

    @Override
    public List<MilestoneStateDetail> findMilestoneDetails(long childId, int month) {
        return query
                .select(Projections.constructor(MilestoneStateDetail.class,
                        milestoneState.id,
                        milestone.title,
                        milestone.content,
                        milestone.requiredAmount,
                        milestoneState.currentAmount,
                        milestoneState.date))
                .from(milestone)
                .join(milestoneState).on(milestoneState.id.eq(milestone.id))
                .where(milestoneState.child.id.eq(childId)
                        .and(milestone.month.eq(month)))
                .orderBy(milestoneState.id.asc())
                .fetch();
    }
}
