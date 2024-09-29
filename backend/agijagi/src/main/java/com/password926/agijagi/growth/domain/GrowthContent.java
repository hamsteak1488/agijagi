package com.password926.agijagi.growth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GrowthContent {

    private final double weight;

    private final double height;

    private final int month;
}
