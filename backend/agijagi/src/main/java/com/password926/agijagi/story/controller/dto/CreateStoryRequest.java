package com.password926.agijagi.story.controller.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateStoryRequest {

    private Long childId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
