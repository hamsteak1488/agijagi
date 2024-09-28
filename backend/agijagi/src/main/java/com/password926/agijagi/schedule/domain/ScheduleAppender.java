package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.member.domain.MemberReader;
import com.password926.agijagi.schedule.infrastructure.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ScheduleAppender {

    private final MemberReader memberReader;
    private final ChildReader childReader;
    private final ChildValidator childValidator;
    private final ScheduleRepository scheduleRepository;

    public void append(
            long memberId,
            long childId,
            ScheduleContent scheduleContent
    ) {
        childValidator.validateWriteAuthority(memberId, childId);
        scheduleRepository.save(Schedule.builder()
                .member(memberReader.read(memberId))
                .child(childReader.read(childId))
                .startDateTime(scheduleContent.getStartDateTime())
                .endDateTime(scheduleContent.getEndDateTime())
                .title(scheduleContent.getTitle())
                .description(scheduleContent.getDescription())
                .createdAt(LocalDateTime.now())
                .build()
        );
    }
}
