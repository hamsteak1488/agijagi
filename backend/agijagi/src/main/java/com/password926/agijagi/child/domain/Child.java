package com.password926.agijagi.child.domain;

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
    @Column(name = "child_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthday;

    private String imageUrl;

    private boolean isDeleted;

    public void remove() {
        isDeleted = true;
    }

    public void update(ChildContent childContent) {
        this.name = childContent.getName();
        this.nickname = childContent.getNickname();
        this.birthday = childContent.getBirthday();
    }
}
