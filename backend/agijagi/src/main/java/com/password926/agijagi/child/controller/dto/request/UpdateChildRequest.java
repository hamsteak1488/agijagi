package com.password926.agijagi.child.controller.dto.request;

import com.password926.agijagi.child.domain.ChildContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UpdateChildRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotNull
    private LocalDate birthday;

    public ChildContent toContent() {
        return ChildContent.of(name, nickname, birthday);
    }
}
