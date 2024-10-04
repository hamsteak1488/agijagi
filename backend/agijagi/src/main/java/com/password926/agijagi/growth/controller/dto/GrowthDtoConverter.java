package com.password926.agijagi.growth.controller.dto;

import com.password926.agijagi.growth.controller.dto.response.ReadGrowthResponse;
import com.password926.agijagi.growth.domain.Growth;

import java.util.List;

public class GrowthDtoConverter {

    public static List<ReadGrowthResponse> convert(List<Growth> growthList) {
        return growthList.stream()
                .map(ReadGrowthResponse::from)
                .toList();
    }
}
