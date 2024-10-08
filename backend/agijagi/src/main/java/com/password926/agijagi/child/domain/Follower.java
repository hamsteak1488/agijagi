package com.password926.agijagi.child.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Follower {

    private long id;

    private long childId;

    private Authority authority;

    private String email;

    private String nickname;

    private String imageUrl;
}
