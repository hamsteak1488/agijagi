package com.password926.agijagi.child.controller.dto;

import com.password926.agijagi.child.controller.dto.response.ReadChildDetailResponse;
import com.password926.agijagi.child.domain.ChildDetail;

import java.util.List;

public class ChildDtoConverter {

    public static List<ReadChildDetailResponse> convert(List<ChildDetail> childDetails) {
        return childDetails.stream()
                .map(ReadChildDetailResponse::from)
                .toList();
    }
}
