package com.password926.agijagi.child.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AppendFollowerRequest {

    @NotNull
    private UUID invitationCode;
}
