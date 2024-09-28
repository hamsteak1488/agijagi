package com.password926.agijagi.child.domain;

import com.password926.agijagi.media.domain.Image;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Convert(converter = GenderConverter.class)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthday;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Image image;

    private boolean isDeleted;

    public void remove() {
        isDeleted = true;
    }

    public void update(ChildContent childContent) {
        this.name = childContent.getName();
        this.gender = childContent.getGender();
        this.nickname = childContent.getNickname();
        this.birthday = childContent.getBirthday();
    }

    public void updateImage(Image image) {
        this.image = image;
    }
}
