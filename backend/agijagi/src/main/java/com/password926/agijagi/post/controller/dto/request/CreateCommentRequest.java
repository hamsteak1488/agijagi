package com.password926.agijagi.post.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private long parentCommentId;

    @NotNull
    private String content;
}
