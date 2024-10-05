package com.password926.agijagi.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDetail {
    private final long commentId;
    private final long writerId;
    private final String writerNickname;
    private final String content;
    private final LocalDateTime createdAt;
}
