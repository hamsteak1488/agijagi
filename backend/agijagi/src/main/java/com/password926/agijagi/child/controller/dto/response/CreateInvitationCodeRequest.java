package com.password926.agijagi.child.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateInvitationCodeRequest {

    private UUID invitationCode;

    public static CreateInvitationCodeRequest of(UUID invitationCode) {
        return new CreateInvitationCodeRequest(invitationCode);
    }
}
