package com.password926.agijagi.report.domain;

import com.password926.agijagi.growth.domain.Growth;
import com.password926.agijagi.growth.domain.StandardGrowth;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReportDetail {

    private final Long id;

    private final int month;

    private final String content;

    private final int growthDegree;

    private final int maxDegree;

    private final LocalDateTime createdAt;

    private final List<Growth> growths;

    private final List<StandardGrowth> standardGrowths;

    public static ReportDetail of(Report report, List<Growth> growths, List<StandardGrowth> standardGrowths) {
        return ReportDetail.builder()
                .id(report.getId())
                .month(report.getMonth())
                .content(report.getContent())
                .growthDegree(report.getGrowthDegree())
                .maxDegree(report.getMaxDegree())
                .createdAt(report.getCreatedAt())
                .growths(growths)
                .standardGrowths(standardGrowths)
                .build();
    }
}
