package com.password926.agijagi.ai.controller.dto.request;

import com.password926.agijagi.ai.domain.Base64Content;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class Base64Request {

    @NotBlank
    private String name;

    @NotBlank
    private String extension;

    @NotBlank
    private String base64Data;

    public Base64Content toContent() {
        return new Base64Content(name, extension, base64Data);
    }
}
