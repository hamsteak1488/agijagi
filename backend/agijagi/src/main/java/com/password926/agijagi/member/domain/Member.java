package com.password926.agijagi.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(nullable = false)
    private ProfileDetail profileDetail;

    @Column(nullable = false)
    private String password;

    @Column
    private Long profileImageId;

    public static Member of(ProfileDetail profileDetail) {
        return builder()
                .profileDetail(profileDetail)
                .build();
    }

    public void updateProfileDetail(ProfileDetail profileDetail) {
        this.profileDetail = profileDetail;
    }

    public void updateProfileImage(long imageId) {
        this.profileImageId = imageId;
    }
}
