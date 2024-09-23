package com.password926.agijagi.member.controller.dto.request;

import com.password926.agijagi.member.domain.ProfileDetail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterMemberRequest {
    @Email
    private final String email;
    @NotBlank
    private final String password;
    @NotBlank
    private final String nickname;

    public ProfileDetail toMemberProfile() {
        return ProfileDetail.of(email, nickname);
    }
}
