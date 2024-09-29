package com.password926.agijagi.report.controller;

import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.report.controller.dto.ReportDtoConverter;
import com.password926.agijagi.report.controller.dto.response.ReadReportDetailResponse;
import com.password926.agijagi.report.controller.dto.response.ReadReportsResponse;
import com.password926.agijagi.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/children")
@RequiredArgsConstructor
@RestController
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{childId}/reports/{reportId}")
    public ResponseEntity<ReadReportDetailResponse> readReportDetail(
            LoginMember member,
            @PathVariable long childId,
            @PathVariable long reportId
    ) {
        return ResponseEntity.ok().body(ReadReportDetailResponse.from(reportService.readReportDetail(member.getId(), childId, reportId)));
    }

    @GetMapping("/{childId}/reports")
    public ResponseEntity<List<ReadReportsResponse>> readReports(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(ReportDtoConverter.convert(reportService.readReports(member.getId(), childId)));
    }

    @PostMapping("/{childId}/reports")
    public ResponseEntity<Void> appendReport(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{childId}/reports/{reportId}")
    public ResponseEntity<Void> removeReport(
            LoginMember member,
            @PathVariable long childId,
            @PathVariable long reportId
    ) {
        reportService.removeReport(member.getId(), childId, reportId);
        return ResponseEntity.ok().build();
    }
}
