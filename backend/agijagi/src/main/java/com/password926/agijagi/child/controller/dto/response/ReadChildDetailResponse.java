package com.password926.agijagi.child.controller.dto.response;

import com.password926.agijagi.child.domain.ChildDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReadChildDetailResponse {

    private long childId;

    private String name;

    private String nickname;

    private String gender;

    private LocalDate birthday;

    private String imageUrl;

    private String authority;

    private long followerNum;

    public static ReadChildDetailResponse from(ChildDetail childDetail) {
        return ReadChildDetailResponse.builder()
                .childId(childDetail.getChildId())
                .name(childDetail.getName())
                .nickname(childDetail.getNickname())
                .gender(childDetail.getGender().getDesc())
                .birthday(childDetail.getBirthday())
                .imageUrl(childDetail.getImageUrl())
                .authority(childDetail.getAuthority().name())
                .followerNum(childDetail.getFollowerNum())
                .build();
    }
}
