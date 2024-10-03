package com.password926.agijagi.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberDetailReader {
    private final MemberReader memberReader;

    @Transactional(readOnly = true)
    public MemberDetail read(long memberId) {
        Member member = memberReader.read(memberId);
        String profileImageUrl = member.getProfileImage() != null ? member.getProfileImage().getUrl() : null;

        return MemberDetail.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
