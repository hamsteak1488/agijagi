package com.password926.agijagi.report.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.report.infrastructure.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ReportRemover {

    private final ChildValidator childValidator;
    private final ReportReader reportReader;
    private final ReportValidator reportValidator;
    private final ReportRepository reportRepository;

    @Transactional
    public void remove(long memberId, long childId, long reportId) {
        childValidator.validateWriteAuthority(memberId, childId);
        Report report = reportReader.read(childId, reportId);
        reportValidator.validateOwner(childId, report);
        reportRepository.removeById(report.getId());
    }
}
