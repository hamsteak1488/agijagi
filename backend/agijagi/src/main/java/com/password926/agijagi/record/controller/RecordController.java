package com.password926.agijagi.record.controller;

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

@RequestMapping("/children/records")
@RequiredArgsConstructor
@RestController
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<ReadRecordResponse>> readRecord(
            long memberId,
            @RequestParam long childId,
            @RequestParam(required = false) String type,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok()
                .body(RecordDtoConverter.convert(recordService.readRecord(memberId, childId, type, startDate, endDate)));
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ReadLatestRecordResponse>> readLatestRecord(
            long memberId,
            @RequestParam long childId
    ) {
        return ResponseEntity.ok()
                .body(RecordDtoConverter.convertToLatest(recordService.readLatestRecord(memberId, childId)));
    }

    @PostMapping
    public ResponseEntity<Void> appendRecord(
            long memberId,
            @RequestBody @Valid AppendRecordRequest request
    ) {
        recordService.appendRecord(memberId, request.getChildId(), request.toContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> removeRecord(
            long memberId,
            @PathVariable long recordId
    ) {
        recordService.removeRecord(memberId, recordId);
        return ResponseEntity.ok().build();
    }
}
