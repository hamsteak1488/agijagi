package com.password926.agijagi.schedule.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.schedule.controller.dto.ScheduleDtoConverter;
import com.password926.agijagi.schedule.controller.dto.request.AppendScheduleRequest;
import com.password926.agijagi.schedule.controller.dto.request.UpdateScheduleRequest;
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

    @Authenticate
    @GetMapping
    public ResponseEntity<List<ReadScheduleResponse>> readSchedule(
            LoginMember member,
            @RequestParam long childId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok()
                .body(ScheduleDtoConverter.convert(scheduleService.readSchedule(member.getId(), childId, startDate, endDate)));
    }

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> appendSchedule(
            LoginMember member,
            @RequestBody @Valid AppendScheduleRequest request
    ) {
        scheduleService.appendSchedule(member.getId(), request.getChildId(), request.toContent());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> removeSchedule(
            LoginMember member,
            @PathVariable long scheduleId
    ) {
        scheduleService.removeSchedule(member.getId(), scheduleId);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            LoginMember member,
            @PathVariable long scheduleId,
            @RequestBody @Valid UpdateScheduleRequest request
    ) {
        scheduleService.updateSchedule(member.getId(), scheduleId, request.toContent());
        return ResponseEntity.ok().build();
    }
}
