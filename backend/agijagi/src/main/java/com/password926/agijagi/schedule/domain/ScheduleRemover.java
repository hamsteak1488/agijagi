package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.schedule.infrastructure.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ScheduleRemover {

    private final ScheduleReader scheduleReader;
    private final ChildValidator childValidator;
    private final ScheduleValidator scheduleValidator;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void remove(long memberId, long childId, long scheduleId) {
        Schedule schedule = scheduleReader.read(scheduleId);
        childValidator.validateWriteAuthority(memberId, schedule.getChild().getId());
        scheduleValidator.validateOwner(childId, schedule);
        scheduleRepository.delete(schedule);
    }
}
