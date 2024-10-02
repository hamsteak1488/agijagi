package com.password926.agijagi.schedule.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleCreatedByAi {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String title;

    private String description;
}
