package com.password926.agijagi.growth.controller.dto.request;

import com.password926.agijagi.growth.domain.GrowthContent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AppendGrowthRequest {

    @NotNull
    private double weight;

    @NotNull
    private double height;

    @NotNull
    private int month;

    public GrowthContent toContent() {
        return new GrowthContent(weight, height, month);
    }
}
