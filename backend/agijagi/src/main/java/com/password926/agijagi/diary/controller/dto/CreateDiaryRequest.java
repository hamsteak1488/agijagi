package com.password926.agijagi.diary.controller.dto;

import lombok.Getter;

@Getter
public class CreateDiaryRequest {

    private Long childId;

    private String title;

    private String content;

}
