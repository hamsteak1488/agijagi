package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class FollowerUpdater {

    private final ChildValidator childValidator;
    private final MemberChildReader memberChildReader;

    @Transactional
    public void update(long memberId, long childId, long followerId, Authority authority) {
        childValidator.validateWriteAuthority(memberId, childId);
        MemberChild memberchild = memberChildReader.readByMemberAndChild(followerId, childId);
        memberchild.update(authority);
    }
}
