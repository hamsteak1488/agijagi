package com.password926.agijagi.report.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.growth.domain.Growth;
import com.password926.agijagi.growth.domain.GrowthReader;
import com.password926.agijagi.growth.domain.StandardGrowth;
import com.password926.agijagi.growth.domain.StandardGrowthReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReportDetailReader {

    private final ChildValidator childValidator;
    private final ReportReader reportReader;
    private final GrowthReader growthReader;
    private final StandardGrowthReader standardGrowthReader;

    public ReportDetail read(long memberId, long childId, long reportId) {
        childValidator.validateWriteAuthority(memberId, childId);
        Report report = reportReader.read(childId, reportId);
        List<Growth> growth = growthReader.readAllByMonth(childId, report.getMonth());
        List<StandardGrowth> standardGrowths = standardGrowthReader.read(report.getMonth());
        return ReportDetail.of(report, growth, standardGrowths);
    }
}
