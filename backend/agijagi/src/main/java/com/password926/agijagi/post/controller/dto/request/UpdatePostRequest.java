package com.password926.agijagi.post.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdatePostRequest {
    @NotNull
    private String title;

    @NotNull
    private String content;

    private List<MultipartFile> newMediaList;

    private List<UUID> deleteMediaIdList;
}
