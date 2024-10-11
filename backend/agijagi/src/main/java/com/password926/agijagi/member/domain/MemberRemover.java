package com.password926.agijagi.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRemover {
    private final MemberReader memberReader;

    @Transactional
    public void remove(long memberId) {
        Member member = memberReader.read(memberId);
        member.delete();
    }
}
