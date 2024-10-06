package com.password926.agijagi.story.controller.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CreateStoryRequest {

    private Long childId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private MultipartFile coverImage;
}
