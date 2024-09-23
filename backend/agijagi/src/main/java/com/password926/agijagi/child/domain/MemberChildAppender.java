package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.controller.ChildErrorCode;
import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberChildAppender {

    private final MemberChildRepository memberChildRepository;

    public void createRelation(Member member, Child child, String role) {
        if (memberChildRepository.existsByMemberIdAndChildId(member.getId(), child.getId())) {
            throw new RestApiException(ChildErrorCode.EXISTING_RELATION);
        }
        memberChildRepository.save(MemberChild.builder()
                .member(member)
                .child(child)
                .role(role)
                .build());
    }
}
