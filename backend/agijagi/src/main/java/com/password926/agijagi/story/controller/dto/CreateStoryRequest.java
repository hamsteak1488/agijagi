package com.password926.agijagi.story.controller.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateStoryRequest {

    // 아이의 정보를 가져오기 위한 childId
    private Long childId;

    // 일기를 구간별로 가져오기 위한 기간
    private String startTime;

    // 일기를 구간별로 가져오기 위한 기간
    private String endTime;

    // 만든 시간
    private LocalDateTime createdAt;

}
