package com.password926.agijagi.report.infrastructure;

import com.password926.agijagi.report.domain.Report;
import com.password926.agijagi.report.domain.ReportTarget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends Repository<Report, Long> {

    @Query("SELECT r.id, r.month, r.createdAt " +
            "FROM Report r " +
            "WHERE r.childId = :childId")
    List<ReportTarget> findReportTargets(long childId);

    Optional<Report> findById(long id);

    void removeById(long id);
}
