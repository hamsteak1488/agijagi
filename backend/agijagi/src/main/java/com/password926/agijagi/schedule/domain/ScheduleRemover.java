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
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void remove(long memberId, long scheduleId) {
        Schedule schedule = scheduleReader.read(scheduleId);
        childValidator.validateWriteAuthority(memberId, schedule.getChild().getId());
        scheduleRepository.delete(schedule);
    }
}
