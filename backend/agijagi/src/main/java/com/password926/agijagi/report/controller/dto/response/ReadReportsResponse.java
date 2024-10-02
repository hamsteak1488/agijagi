package com.password926.agijagi.report.controller.dto.response;

import com.password926.agijagi.report.domain.ReportTarget;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadReportsResponse {

    private long id;

    private int month;

    private LocalDateTime createAt;

    public static ReadReportsResponse from(ReportTarget reportTarget) {
        return ReadReportsResponse.builder()
                .id(reportTarget.getId())
                .month(reportTarget.getMonth())
                .createAt(reportTarget.getCreatedAt())
                .build();
    }
}
