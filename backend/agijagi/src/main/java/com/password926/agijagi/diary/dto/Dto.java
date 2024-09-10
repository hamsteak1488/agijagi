package com.password926.agijagi.diary.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Dto {
    private Long childId;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime date;
}
