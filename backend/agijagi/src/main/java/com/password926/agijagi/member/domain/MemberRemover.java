package com.password926.agijagi.member.domain;

import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRemover {
    private final MemberRepository memberRepository;
    
    // TODO: Soft Delete 방식 사용할 것
    @Transactional
    public void remove(long memberId) {
        memberRepository.deleteById(memberId);
    }
}
