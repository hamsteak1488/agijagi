package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ScheduleUpdater {

    private final ChildValidator childValidator;
    private final ScheduleValidator scheduleValidator;
    private final ScheduleReader scheduleReader;

    @Transactional
    public void update(
            long memberId,
            long childId,
            long scheduleId,
            ScheduleContent scheduleContent
    ) {
        Schedule schedule = scheduleReader.read(scheduleId);
        childValidator.validateWriteAuthority(memberId, schedule.getChild().getId());
        scheduleValidator.validateOwner(childId, schedule);
        schedule.update(scheduleContent);
    }
}
