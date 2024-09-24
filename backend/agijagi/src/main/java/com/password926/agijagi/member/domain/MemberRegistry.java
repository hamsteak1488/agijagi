package com.password926.agijagi.member.domain;

import com.password926.agijagi.auth.controller.AuthErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRegistry {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public long register(ProfileDetail profileDetail, String password) {
        memberRepository.findByProfileDetailEmail(profileDetail.getEmail())
                .ifPresent(member -> { throw new RestApiException(AuthErrorCode.EMAIL_DUPLICATED); } );

        String encodePassword = bCryptPasswordEncoder.encode(password);
        Member member = memberRepository.save(Member.of(profileDetail, encodePassword));
        return member.getId();
    }
}
