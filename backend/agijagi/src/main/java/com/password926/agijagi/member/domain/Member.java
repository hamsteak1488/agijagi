package com.password926.agijagi.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private Long profileImageId;
}
