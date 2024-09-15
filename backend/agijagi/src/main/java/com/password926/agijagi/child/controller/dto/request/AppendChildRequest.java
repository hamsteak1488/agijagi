package com.password926.agijagi.child.controller.dto.request;

import com.password926.agijagi.child.domain.ChildContent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
public class AppendChildRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private LocalDate birthday;

    private MultipartFile image;

    public ChildContent toContent() {
        return ChildContent.of(name, nickname, birthday);
    }
}
