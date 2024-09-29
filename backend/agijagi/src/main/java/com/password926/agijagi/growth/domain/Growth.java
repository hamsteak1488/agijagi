package com.password926.agijagi.growth.domain;

import com.password926.agijagi.child.domain.Child;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void update(GrowthContent growthContent) {
        this.weight = growthContent.getWeight();
        this.height = growthContent.getHeight();
        this.month = growthContent.getMonth();
    }
}
