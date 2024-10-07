package com.password926.agijagi.story.entity;

import lombok.*;

import java.time.*;

@Getter
@Builder
public class StoryDetail {

    private long id;

    private long childId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private Short coverImageUrl;

    public static StoryDetail of(Story story) {
        return StoryDetail.builder()
                .id(story.getId())
                .childId(story.getChild().getId())
                .title(story.getTitle())
                .startDate(story.getStartDate())
                .endDate(story.getEndDate())
                .createdAt(story.getCreatedAt())
                .coverImageUrl(story.getCoverImageIndex())
                .build();
    }
}
