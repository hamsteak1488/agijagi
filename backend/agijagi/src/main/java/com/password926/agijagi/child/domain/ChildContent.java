package com.password926.agijagi.child.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ChildContent {

    private String name;

    private String nickname;

    private Gender gender;

    private LocalDate birthday;

    public static ChildContent of(String name, String nickname, Gender gender, LocalDate birthday) {
        return ChildContent.builder()
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .birthday(birthday)
                .build();
    }
}
