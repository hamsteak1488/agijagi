package com.password926.agijagi.schedule.controller.dto;

import com.password926.agijagi.schedule.controller.dto.response.ReadScheduleResponse;
import com.password926.agijagi.schedule.domain.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleDtoConverter {

    public static List<ReadScheduleResponse> convert(List<Schedule> schedules) {
        return schedules.stream()
                .map(ReadScheduleResponse::from)
                .collect(Collectors.toList());
    }
}
