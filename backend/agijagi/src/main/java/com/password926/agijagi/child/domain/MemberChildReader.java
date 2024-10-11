package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MemberChildReader {

    private final MemberChildRepository memberChildRepository;

    public List<MemberChild> readByMember(long memberId) {
        return memberChildRepository.findByMemberId(memberId);
    }

    public MemberChild readByMemberAndChild(long memberId, long childId) {
        return memberChildRepository.findByMemberIdAndChildId(memberId, childId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
