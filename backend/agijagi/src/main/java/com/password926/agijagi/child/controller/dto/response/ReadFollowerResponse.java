package com.password926.agijagi.child.controller.dto.response;

import com.password926.agijagi.child.domain.Follower;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadFollowerResponse {

    private long followerId;

    private String nickname;

    private String authority;

    private String imageUrl;

    public static ReadFollowerResponse from(Follower follower) {
        return new ReadFollowerResponse(
                follower.getId(),
                follower.getNickname(),
                follower.getAuthority().name(),
                follower.getImageUrl()
        );
    }
}
