package com.password926.agijagi.member.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetail {
    private final long memberId;
    private final String email;
    private final String nickname;
    private final String profileImageUrl;
}
