package com.password926.agijagi.schedule.controller.dto.response;

import com.password926.agijagi.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadScheduleResponse {

    private long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String title;

    private String description;

    public static ReadScheduleResponse from(Schedule schedule) {
        return ReadScheduleResponse.builder()
                .id(schedule.getId())
                .startDateTime(schedule.getStartDateTime())
                .endDateTime(schedule.getEndDateTime())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .build();
    }
}
