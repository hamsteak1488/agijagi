package com.password926.agijagi.diary.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Builder
public class DiaryDetail {

    private long id;

    private long childId;

    private long memberId;

    private String content;

    private LocalDate wroteAt;

    private LocalDateTime createdAt;

    private List<String> mediaUrls;

    public static DiaryDetail of(Diary diary) {
        return DiaryDetail.builder()
                .id(diary.getId())
                .childId(diary.getChild().getId())
                .memberId(diary.getMember().getId())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .wroteAt(diary.getWroteAt())
                .mediaUrls(new ArrayList<>())
                .build();
    }
}
