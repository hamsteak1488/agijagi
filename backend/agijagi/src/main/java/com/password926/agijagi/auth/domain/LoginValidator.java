package com.password926.agijagi.auth.domain;

import com.password926.agijagi.auth.controller.AuthErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginValidator {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberReader memberReader;

    public long validate(String email, String password) {
        Member member = memberReader.readByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            throw new RestApiException(AuthErrorCode.PASSWORD_NOT_MATCH);
        }

        return member.getId();
    }
}
