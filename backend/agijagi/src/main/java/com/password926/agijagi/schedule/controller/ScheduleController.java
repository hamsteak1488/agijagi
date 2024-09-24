package com.password926.agijagi.schedule.controller;

import com.password926.agijagi.schedule.controller.dto.ScheduleDtoConverter;
import com.password926.agijagi.schedule.controller.dto.request.AppendScheduleRequest;
import com.password926.agijagi.schedule.controller.dto.response.ReadScheduleResponse;
import com.password926.agijagi.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/schedules")
@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ReadScheduleResponse>> readSchedule(
            long memberId,
            @RequestParam long childId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok()
                .body(ScheduleDtoConverter.convert(scheduleService.readSchedule(memberId, childId, startDate, endDate)));
    }

    @PostMapping
    public ResponseEntity<Void> appendSchedule(
            long memberId,
            @RequestBody @Valid AppendScheduleRequest request
    ) {
        scheduleService.appendSchedule(memberId, request.getChildId(), request.toContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> removeSchedule(
            long memberId,
            @RequestParam long scheduleId
    ) {
        scheduleService.removeSchedule(memberId, scheduleId);
        return ResponseEntity.ok().build();
    }
}
