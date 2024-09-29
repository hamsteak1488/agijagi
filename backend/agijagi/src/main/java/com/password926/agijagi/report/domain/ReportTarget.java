package com.password926.agijagi.report.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReportTarget {

    private long id;

    private int month;

    private LocalDateTime createdAt;
}
