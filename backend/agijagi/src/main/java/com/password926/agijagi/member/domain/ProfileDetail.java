package com.password926.agijagi.member.domain;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileDetail {
    private String email;
    private String nickname;

    public static ProfileDetail of(String email, String nickname) {
        return ProfileDetail.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
