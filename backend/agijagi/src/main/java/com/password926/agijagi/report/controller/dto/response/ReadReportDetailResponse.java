package com.password926.agijagi.report.controller.dto.response;

import com.password926.agijagi.growth.domain.Growth;
import com.password926.agijagi.growth.domain.StandardGrowth;
import com.password926.agijagi.report.domain.ReportDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@Builder
public class ReadReportDetailResponse {

    private double currWeight;

    private String content;

    private int growthDegree;

    private int maxDegree;

    private LocalDateTime createdAt;

    private List<GraphDataContent> graphData;

    public static ReadReportDetailResponse from(ReportDetail reportDetail) {
        List<Growth> growths = reportDetail.getGrowths();
        List<StandardGrowth> standardGrowths = reportDetail.getStandardGrowths();

        List<GraphDataContent> graphData = IntStream.range(0, reportDetail.getGrowths().size())
                .mapToObj(month -> GraphDataContent.of(
                        month,
                        growths.get(month),
                        standardGrowths.get(month)
                ))
                .toList();
        double currWeight = growths.isEmpty() ? 0 : growths.get(growths.size() - 1).getWeight();

        return ReadReportDetailResponse.builder()
                .currWeight(currWeight)
                .content(reportDetail.getContent())
                .growthDegree(reportDetail.getGrowthDegree())
                .maxDegree(reportDetail.getMaxDegree())
                .createdAt(reportDetail.getCreatedAt())
                .graphData(graphData)
                .build();
    }

    @Getter
    @AllArgsConstructor
    private static class GraphDataContent {
        int day;
        double weight;
        double standardWeight;

        public static GraphDataContent of(int month, Growth growth, StandardGrowth standardGrowth) {
            return new GraphDataContent(
                    30 * month,
                    growth.getWeight(),
                    standardGrowth.getStandardWeight()
            );
        }
    }
}
