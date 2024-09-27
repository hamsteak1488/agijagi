package com.password926.agijagi.diary.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DiaryDetail {

    private long id;

    private long childId;

    private long memberId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    public static DiaryDetail of(Diary diary) {
        return DiaryDetail.builder()
                .id(diary.getId())
                .childId(diary.getChild().getId())
                .memberId(diary.getMember().getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .build();
    }
}
