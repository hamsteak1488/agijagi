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

    public Report read(long childId, long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
        return report;
    }

    public List<ReportTarget> readAll(long memberId, long childId) {
        childValidator.validateReadAuthority(memberId, childId);
        return reportRepository.findByChildId(childId);
    }
}
