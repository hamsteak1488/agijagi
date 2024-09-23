package com.password926.agijagi.milestone.domain;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MilestoneStateDetail {

    private long id;

    private String title;

    private String content;

    private int requiredAmount;

    private int currentAmount;

    private LocalDate date;
}
