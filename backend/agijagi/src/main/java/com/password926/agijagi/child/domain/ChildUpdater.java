package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChildUpdater {

    private final ChildValidator childValidator;
    private final ChildReader childReader;

    public void update(long memberId, long childId, ChildContent childContent) {
        childValidator.validateWriterRole(memberId, childId);
        childReader.read(childId) .update(childContent);
    }
}
