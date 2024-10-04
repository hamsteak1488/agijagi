package com.password926.agijagi.growth.controller.dto.response;

import com.password926.agijagi.growth.domain.Growth;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadGrowthResponse {

    private double weight;

    private double height;

    private int month;

    public static ReadGrowthResponse from(Growth growth) {
        return ReadGrowthResponse.builder()
                .weight(growth.getWeight())
                .height(growth.getHeight())
                .month(growth.getMonth())
                .build();
    }
}
