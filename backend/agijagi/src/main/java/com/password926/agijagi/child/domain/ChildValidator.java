package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.controller.ChildErrorCode;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChildValidator {

    private final MemberChildReader memberChildReader;

    public void validateWriterRole(long memberId, long childId) {
        MemberChild memberChild = memberChildReader.readByMemberAndChild(memberId, childId);
        if (!"WRITE".equals(memberChild.getRole())) {
            throw new RestApiException(CommonErrorCode.FORBIDDEN);
        }
    }
}
