package com.password926.agijagi.record.controller;

import com.password926.agijagi.record.controller.dto.request.AppendRecordRequest;
import com.password926.agijagi.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/records")
@RequiredArgsConstructor
@RestController
public class RecordController {

    private final RecordService recordService;

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
