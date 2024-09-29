package com.password926.agijagi.report.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.report.infrastructure.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReportReader {

    private final ChildValidator childValidator;
    private final ReportRepository reportRepository;
    private final ReportValidator reportValidator;

    public Report read(long childId, long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
        reportValidator.validateOwner(childId, report);
        return report;
    }

    // TODO: 보고서 읽기는 권한 어디까지 허용?
    public List<ReportTarget> readAll(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        return reportRepository.findReportTargets(childId);
    }
}
