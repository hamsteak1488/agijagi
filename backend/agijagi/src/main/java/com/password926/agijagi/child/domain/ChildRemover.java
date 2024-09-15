package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.controller.ChildErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChildRemover {

    private final ChildReader childReader;
    private final MemberChildReader memberChildReader;
    private final MemberChildRemover memberChildRemover;

    @Transactional
    public void remove(long memberId, long childId) {
        MemberChild memberChild = memberChildReader.readByMemberAndChild(memberId, childId);
        if (!"WRITE".equals(memberChild.getRole())) {
            throw new RestApiException(ChildErrorCode.UNAUTHORIZED);
        }
        childReader.read(childId).remove();
        //TODO: relation 삭제 어떻게 할 지 정하고 다시 (soft or hard)
        memberChildRemover.removeAllRelation(childId);
    }
}
