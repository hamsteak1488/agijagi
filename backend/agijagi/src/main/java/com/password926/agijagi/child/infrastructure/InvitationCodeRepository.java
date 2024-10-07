package com.password926.agijagi.child.infrastructure;

import com.password926.agijagi.child.domain.InvitationCode;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationCodeRepository extends Repository<InvitationCode, UUID> {

    Optional<InvitationCode> findById(UUID id);

    void save(InvitationCode invitationCode);
}
