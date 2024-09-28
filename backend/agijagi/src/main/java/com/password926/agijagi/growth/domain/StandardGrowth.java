package com.password926.agijagi.growth.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class StandardGrowth {

    @Id
    private Integer id;

    private double standardWeight;

    private int month;
}
