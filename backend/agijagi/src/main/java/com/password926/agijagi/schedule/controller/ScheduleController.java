package com.password926.agijagi.schedule.controller;

import com.password926.agijagi.schedule.controller.dto.ScheduleDtoConverter;
import com.password926.agijagi.schedule.controller.dto.response.ReadScheduleResponse;
import com.password926.agijagi.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
