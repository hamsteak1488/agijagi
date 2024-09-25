package com.password926.agijagi.schedule.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleContent {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String title;

    private String description;

    public static ScheduleContent of(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, String description) {
        return ScheduleContent.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .title(title)
                .description(description)
                .build();
    }
}
