package com.password926.agijagi.ai.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Base64Content {

    private final String base64Data;

    private final String name;

    private final String extension;
}
