package com.password926.agijagi.story.controller.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletionRequest {

    private String model;

    private String prompt;

    private float temperature;

    @Builder
    CompletionRequest(String model, String prompt, float temperature) {
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
    }

}
