package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.controller.ChildErrorCode;
import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class FollowerAppender {

    private final MemberChildRepository memberChildRepository;
    private final MemberReader memberReader;
    private final ChildReader childReader;

    @Transactional
    public void append(long memberId, long childId, Authority authority) {
        if (memberChildRepository.existsByMemberIdAndChildId(memberId, childId)) {
            throw new RestApiException(ChildErrorCode.EXISTING_RELATION);
        }
        memberChildRepository.save(MemberChild.builder()
                .member(memberReader.read(memberId))
                .child(childReader.read(childId))
                .authority(authority)
                .build());
    }
}
