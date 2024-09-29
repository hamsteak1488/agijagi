package com.password926.agijagi.report.controller.dto;

import com.password926.agijagi.report.controller.dto.response.ReadReportsResponse;
import com.password926.agijagi.report.domain.ReportTarget;

import java.util.List;

public class ReportDtoConverter {

    public static List<ReadReportsResponse> convert(List<ReportTarget> reportTargets) {
        return reportTargets.stream()
                .map(ReadReportsResponse::from)
                .toList();
    }
}
