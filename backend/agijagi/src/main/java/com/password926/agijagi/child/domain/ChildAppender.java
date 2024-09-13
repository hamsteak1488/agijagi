package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChildAppender {

    private final ChildRepository childRepository;

    public void append(long memberId, ChildContent childContent, String url) {

    }
}
