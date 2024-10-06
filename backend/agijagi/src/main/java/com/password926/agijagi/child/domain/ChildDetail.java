package com.password926.agijagi.child.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ChildDetail {

    private long childId;

    private String name;

    private String nickname;

    private Gender gender;

    private Double birthWeight;

    private Double birthHeight;

    private LocalDate birthday;

    private String imageUrl;

    private Authority authority;

    private long followerNum;

    public static ChildDetail of(Child child, Authority authority, long followerNum) {
        String imageUrl = child.getImage() == null ? null : child.getImage().getUrl();
        return ChildDetail.builder()
                .childId(child.getId())
                .name(child.getName())
                .nickname(child.getNickname())
                .gender(child.getGender())
                .birthWeight(child.getBirthWeight())
                .birthHeight(child.getBirthHeight())
                .birthday(child.getBirthday())
                .imageUrl(imageUrl)
                .authority(authority)
                .followerNum(followerNum)
                .build();
    }
}
