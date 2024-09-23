package com.password926.agijagi.schedule.service;

import com.password926.agijagi.schedule.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleReader scheduleReader;
    private final ScheduleAppender scheduleAppender;
    private final ScheduleRemover scheduleRemover;

    public List<Schedule> readSchedule(
            long memberId,
            long childId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return scheduleReader.readByDates(memberId, childId, startDate, endDate);
    }

    public void appendSchedule(
            long memberId,
            long childId,
            ScheduleContent scheduleContent
    ) {
        scheduleAppender.append(memberId, childId, scheduleContent);
    }

    public void removeSchedule(long memberId, long scheduleId) {
        scheduleRemover.remove(memberId, scheduleId);
    }
}
