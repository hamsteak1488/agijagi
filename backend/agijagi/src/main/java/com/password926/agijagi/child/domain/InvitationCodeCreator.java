package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.InvitationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InvitationCodeCreator {

    private final ChildValidator childValidator;
    private final InvitationCodeRepository invitationCodeRepository;

    public UUID create(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        InvitationCode invitationCode = InvitationCode.builder()
                .childId(childId)
                .createdAt(LocalDateTime.now())
                .build();
        invitationCodeRepository.save(invitationCode);
        return invitationCode.getId();
    }
}
