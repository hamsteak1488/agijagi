package com.password926.agijagi.auth.domain;

import com.password926.agijagi.auth.controller.AuthErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginValidator {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public long validate(String email, String password) {
        Member member = memberRepository.findByProfileDetailEmail(email)
                .orElseThrow(() -> new RestApiException(AuthErrorCode.EMAIL_NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new RestApiException(AuthErrorCode.PASSWORD_NOT_MATCH);
        }

        return member.getId();
    }
}
