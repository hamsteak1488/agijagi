package com.password926.agijagi.report.infrastructure;

import com.password926.agijagi.report.domain.Report;
import com.password926.agijagi.report.domain.ReportTarget;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends Repository<Report, Long> {

    List<ReportTarget> findByChildId(long childId);

    Optional<Report> findById(long id);

    void removeById(long id);

    void save(Report report);
}
