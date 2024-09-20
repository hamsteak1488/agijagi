package com.password926.agijagi.child.domain;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class ChildDetail {

    private long childId;

    private String name;

    private String nickname;

    private LocalDate birthday;

    private String imageUrl;

    private String role;

    private long followerNum;

    public static ChildDetail of(Child child, String role, long followerNum) {
        return ChildDetail.builder()
                .childId(child.getId())
                .name(child.getName())
                .nickname(child.getNickname())
                .birthday(child.getBirthday())
                .imageUrl(child.getImageUrl())
                .role(role)
                .followerNum(followerNum)
                .build();
    }
}
