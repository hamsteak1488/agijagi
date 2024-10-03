package com.password926.agijagi.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDetail {
    private final long postId;
    private final String title;
    private final String content;
    private final long writerId;
    private final String writerNickname;
    private final LocalDateTime createdAt;
    private final List<String> mediaUrls;
}
