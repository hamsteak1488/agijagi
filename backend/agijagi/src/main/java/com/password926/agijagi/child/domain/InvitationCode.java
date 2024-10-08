package com.password926.agijagi.child.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InvitationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private long childId;

    private LocalDateTime createdAt;

    private boolean isDeleted;
}
