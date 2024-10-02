package com.password926.agijagi.story.entity;

import lombok.*;

@Getter
@Builder
public class StoryPageDetail {

    private long id;

    private long storyId;

    private int pageNumber;

    private String content;

    public static StoryPageDetail of(StoryPage storyPage) {
        return StoryPageDetail.builder()
                .id(storyPage.getId())
                .storyId(storyPage.getStory().getId())
                .pageNumber(storyPage.getPageNumber())
                .content(storyPage.getContent())
                .build();
    }
}
