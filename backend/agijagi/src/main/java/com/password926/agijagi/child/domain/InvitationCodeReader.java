package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.InvitationCodeRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InvitationCodeReader {

    private final InvitationCodeRepository invitationCodeRepository;

    public InvitationCode read(UUID invitationCodeId) {
        return invitationCodeRepository.findById(invitationCodeId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
