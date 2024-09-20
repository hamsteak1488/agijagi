package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChildRemover {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final MemberChildRemover memberChildRemover;

    @Transactional
    public void remove(long memberId, long childId) {
        childValidator.validateWriterRole(memberId, childId);
        childReader.read(childId).remove();
        //TODO: relation 삭제 어떻게 할 지 정하고 다시 (soft or hard)
        memberChildRemover.removeAllRelation(childId);
    }
}
