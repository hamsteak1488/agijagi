package com.password926.agijagi.diary.controller.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateDiaryRequest {

    private Long childId;

    private String content;

    private LocalDate wroteAt;

    private List<MultipartFile> mediaList;
}
