package com.password926.agijagi.child.controller.dto.request;

import com.password926.agijagi.child.domain.ChildContent;
import com.password926.agijagi.child.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AppendChildRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String gender;

    @NotNull
    private LocalDate birthday;

    private MultipartFile image;

    public ChildContent toContent() {
        return ChildContent.of(name, nickname, Gender.of(gender), birthday);
    }
}
