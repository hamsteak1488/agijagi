package com.password926.agijagi.member.controller.dto.request;

import com.password926.agijagi.member.domain.ProfileDetail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterMemberRequest {
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    public ProfileDetail toMemberProfile() {
        return ProfileDetail.of(email, nickname);
    }
}
