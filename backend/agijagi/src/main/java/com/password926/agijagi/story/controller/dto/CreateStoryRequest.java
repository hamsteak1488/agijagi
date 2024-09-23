package com.password926.agijagi.story.controller.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CreateStoryRequest {

    private Long childId;

    private LocalDate startTime;

    private LocalDate endTime;

}
