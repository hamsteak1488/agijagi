package com.password926.agijagi.member.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterMemberRequest {
    @Email
    private String email;

    @Size(min = 2, max = 20)
    private String password;

    @Size(min = 2, max = 20)
    private String nickname;

    private MultipartFile profileImage;
}
