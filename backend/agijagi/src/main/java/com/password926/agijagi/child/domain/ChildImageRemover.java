package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChildImageRemover {

    private final ChildValidator childValidator;
    private final ChildReader childReader;

    @Transactional
    public void remove(long memberId, long childId) {
        childValidator.validateWriterRole(memberId, childId);
        childReader.read(childId).updateImage(null);
    }
}
