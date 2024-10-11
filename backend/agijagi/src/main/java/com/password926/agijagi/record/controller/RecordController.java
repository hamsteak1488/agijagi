package com.password926.agijagi.record.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.record.controller.dto.RecordDtoConverter;
import com.password926.agijagi.record.controller.dto.request.AppendRecordRequest;
import com.password926.agijagi.record.controller.dto.response.ReadLatestRecordResponse;
import com.password926.agijagi.record.controller.dto.response.ReadRecordResponse;
import com.password926.agijagi.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/children")
@RequiredArgsConstructor
@RestController
public class RecordController {

    private final RecordService recordService;

    @Authenticate
    @GetMapping("/{childId}/records")
    public ResponseEntity<List<ReadRecordResponse>> readRecord(
            LoginMember member,
            @PathVariable long childId,
            @RequestParam(required = false) String type,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok()
                .body(RecordDtoConverter.convert(recordService.readRecord(member.getId(), childId, type, startDate, endDate)));
    }

    @Authenticate
    @GetMapping("/{childId}/records/latest")
    public ResponseEntity<List<ReadLatestRecordResponse>> readLatestRecord(
            LoginMember member,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok()
                .body(RecordDtoConverter.convertToLatest(recordService.readLatestRecord(member.getId(), childId)));
    }

    @Authenticate
    @PostMapping("/{childId}/records")
    public ResponseEntity<Void> appendRecord(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid AppendRecordRequest request
    ) {
        recordService.appendRecord(member.getId(), childId, request.toContent());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{childId}/records/{recordId}")
    public ResponseEntity<Void> removeRecord(
            LoginMember member,
            @PathVariable long childId,
            @PathVariable long recordId
    ) {
        recordService.removeRecord(member.getId(), childId, recordId);
        return ResponseEntity.ok().build();
    }
}
