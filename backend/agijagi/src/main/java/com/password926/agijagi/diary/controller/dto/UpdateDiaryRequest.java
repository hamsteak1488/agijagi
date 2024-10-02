package com.password926.agijagi.diary.controller.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateDiaryRequest {

    private String title;

    private String content;

    private List<MultipartFile> newMediaList;

    private List<UUID> removeMediaIdList;
}
