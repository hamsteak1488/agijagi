package com.password926.agijagi.member.domain;

import com.password926.agijagi.media.domain.Image;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isDeleted;

    public static Member createMember(String email, String password, String nickname, Image profileImage) {
        return builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .createdAt(LocalDateTime.now())
                .profileImage(profileImage)
                .isDeleted(false)
                .build();
    }

    public void update(String newEmail, String newPassword, String newNickname) {
        if (email != null) {
            this.email = newEmail;
        }
        if (password != null) {
            this.password = newPassword;
        }
        if (newNickname != null) {
            this.nickname = newNickname;
        }
    }

    public void updateProfileImage(Image newProfileImage) {
        this.profileImage = newProfileImage;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
