package com.password926.agijagi.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProfileDetail {
    private final String email;
    private final String nickname;

    public static ProfileDetail of(String email, String nickname) {
        return ProfileDetail.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
