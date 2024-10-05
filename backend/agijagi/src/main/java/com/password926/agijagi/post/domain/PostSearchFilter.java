package com.password926.agijagi.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSearchFilter {
    private final String title;
    private final String writerNickname;
}