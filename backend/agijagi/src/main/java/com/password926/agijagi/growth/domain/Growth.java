package com.password926.agijagi.growth.domain;

import com.password926.agijagi.child.domain.Child;
import jakarta.persistence.*;

@Entity
public class Growth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId", nullable = false)
    private Child child;

    private double weight;

    private double height;

    private int month;
}
