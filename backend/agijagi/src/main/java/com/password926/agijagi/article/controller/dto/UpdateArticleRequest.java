package com.password926.agijagi.article.controller.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateArticleRequest {

    private String title;

    private String content;

    private List<MultipartFile> newMediaList;

    private List<Long> removeMediaIdList;
}
