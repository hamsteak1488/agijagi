package com.password926.agijagi.diary.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateDiaryRequest {

    private Long childId;

    private String title;

    private String content;

    private List<MultipartFile> mediaList;

}
