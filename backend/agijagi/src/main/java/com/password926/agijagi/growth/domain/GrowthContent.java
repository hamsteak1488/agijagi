package com.password926.agijagi.growth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GrowthContent {

    private final double weight;

    private final double height;

    private final int month;

    public static GrowthContent from(Growth growth) {
        return new GrowthContent(
                growth.getWeight(),
                growth.getHeight(),
                growth.getMonth()
        );
    }
}
