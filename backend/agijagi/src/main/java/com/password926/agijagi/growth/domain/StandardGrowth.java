package com.password926.agijagi.growth.domain;

import jakarta.persistence.*;

@Entity
public class StandardGrowth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double standardWeight;

    private double standardHeight;

    private int month;
}
