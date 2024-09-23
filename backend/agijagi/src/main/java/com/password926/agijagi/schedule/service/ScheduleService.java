package com.password926.agijagi.schedule.service;

import com.password926.agijagi.schedule.domain.Schedule;
import com.password926.agijagi.schedule.domain.ScheduleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleReader scheduleReader;

    public List<Schedule> readSchedule(
            long memberId,
            long childId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return scheduleReader.readByDates(memberId, childId, startDate, endDate);
    }
}
