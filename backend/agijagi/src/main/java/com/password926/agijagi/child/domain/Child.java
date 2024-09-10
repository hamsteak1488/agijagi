package com.password926.agijagi.child.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private Long id;

    private String name;

    private String nickname;

    private LocalDate birthday;

    @OneToMany(mappedBy = "member_child", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MemberChild> memberChild = new ArrayList<>();

}
