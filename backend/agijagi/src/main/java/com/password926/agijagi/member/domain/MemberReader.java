package com.password926.agijagi.member.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.member.controller.MemberErrorCode;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member read(long memberId) {
        return memberRepository.findByIdAndIsDeletedIsFalse(memberId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Member readByEmail(String email) {
        return memberRepository.findByEmailAndIsDeletedIsFalse(email)
                .orElseThrow(() -> new RestApiException(MemberErrorCode.EMAIL_NOT_FOUND));
    }
}
