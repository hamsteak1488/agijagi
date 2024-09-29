package com.password926.agijagi.growth.domain;

import com.password926.agijagi.child.domain.Gender;
import com.password926.agijagi.child.domain.GenderConverter;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class StandardGrowth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double standardWeight;

    @Column(nullable = false)
    private int month;

    @Convert(converter = GenderConverter.class)
    @Column(nullable = false)
    private Gender gender;
}
