package com.password926.agijagi.child.controller.dto;

import com.password926.agijagi.child.controller.dto.response.ReadChildDetailResponse;
import com.password926.agijagi.child.controller.dto.response.ReadFollowerResponse;
import com.password926.agijagi.child.domain.ChildDetail;
import com.password926.agijagi.child.domain.Follower;

import java.util.List;

public class ChildDtoConverter {

    public static List<ReadChildDetailResponse> convert(List<ChildDetail> childDetails) {
        return childDetails.stream()
                .map(ReadChildDetailResponse::from)
                .toList();
    }

    public static List<ReadFollowerResponse> convertFollowers(List<Follower> followers) {
        return followers.stream()
                .map(ReadFollowerResponse::from)
                .toList();
    }
}
