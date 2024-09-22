package com.password926.agijagi.member.domain;

import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRegistry {
    private final MemberRepository memberRepository;

    @Transactional
    public long register(ProfileDetail profileDetail, String password) {
        Member member = memberRepository.save(Member.of(profileDetail, password));
        return member.getId();
    }
}
