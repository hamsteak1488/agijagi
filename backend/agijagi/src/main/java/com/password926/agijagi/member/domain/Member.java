package com.password926.agijagi.member.domain;

import com.password926.agijagi.media.entity.Image;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    public static Member of(ProfileDetail profileDetail, String password) {
        return builder()
                .profileDetail(profileDetail)
                .password(password)
                .build();
    }

    public void updateProfileDetail(ProfileDetail profileDetail) {
        this.profileDetail = profileDetail;
    }

    public void updateProfileImage(Image image) {
        this.profileImage = image;
    }
}
