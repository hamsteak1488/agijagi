package com.password926.agijagi.report.service;

import com.password926.agijagi.report.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportDetailReader reportDetailReader;
    private final ReportReader reportReader;
    private final ReportAppender reportAppender;
    private final ReportRemover reportRemover;

    public ReportDetail readReportDetail(
            long memberId,
            long childId,
            long reportId
    ) {
        return reportDetailReader.read(memberId, childId, reportId);
    }

    public List<ReportTarget> readReports(long memberId, long childId) {
        return reportReader.readAll(memberId, childId);
    }

    public void appendReport(long memberId, long childId) {
        reportAppender.append(memberId, childId);
    }

    public void removeReport(
            long memberId,
            long childId,
            long reportId
    ) {
        reportRemover.remove(memberId, childId, reportId);
    }
}
