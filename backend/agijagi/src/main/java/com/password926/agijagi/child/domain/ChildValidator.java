package com.password926.agijagi.child.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChildValidator {

    private final MemberChildReader memberChildReader;

    public void validateWriteAuthority(long memberId, long childId) {
        Authority authority = memberChildReader.readByMemberAndChild(memberId, childId)
                .getAuthority();

        if (authority == null || !authority.isWriteAuthority()) {
            throw new RestApiException(CommonErrorCode.FORBIDDEN);
        }
    }
}
