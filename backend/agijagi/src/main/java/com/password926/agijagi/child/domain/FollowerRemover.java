package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class FollowerRemover {

    private final ChildValidator childValidator;
    private final MemberChildReader memberChildReader;
    private final MemberChildRepository memberChildRepository;

    @Transactional
    public void remove(long memberId, long childId, long followerId) {
        childValidator.validateWriteAuthority(childId, memberId);
        MemberChild memberChild = memberChildReader.readByMemberAndChild(followerId, childId);
        memberChildRepository.delete(memberChild);
    }
}
