package com.password926.agijagi.child.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateFollowerRequest {

    private long followerId;

    @NotBlank
    private String authority;
}
