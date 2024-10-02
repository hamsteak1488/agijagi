package com.password926.agijagi.schedule.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.schedule.controller.dto.ScheduleDtoConverter;
import com.password926.agijagi.schedule.controller.dto.request.AppendScheduleRequest;
import com.password926.agijagi.schedule.controller.dto.request.AppendVoiceScheduleRequest;
import com.password926.agijagi.schedule.controller.dto.request.UpdateScheduleRequest;
import com.password926.agijagi.schedule.controller.dto.response.ReadScheduleResponse;
import com.password926.agijagi.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/children")
@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Authenticate
    @GetMapping("/{childId}/schedules")
    public ResponseEntity<List<ReadScheduleResponse>> readSchedule(
            LoginMember member,
            @PathVariable long childId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok()
                .body(ScheduleDtoConverter.convert(scheduleService.readSchedule(member.getId(), childId, startDate, endDate)));
    }

    @Authenticate
    @PostMapping("/{childId}/schedules")
    public ResponseEntity<Void> appendSchedule(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid AppendScheduleRequest request
    ) {
        scheduleService.appendSchedule(member.getId(), childId, request.toContent());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PostMapping("/{childId}/schedules/voice")
    public ResponseEntity<Void> appendVoiceSchedule(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid AppendVoiceScheduleRequest request
    ) {
        scheduleService.appendVoiceSchedule(member.getId(), childId, request.toContent());
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{childId}/schedules/{scheduleId}")
    public ResponseEntity<Void> removeSchedule(
            LoginMember member,
            @PathVariable long childId,
            @PathVariable long scheduleId
    ) {
        scheduleService.removeSchedule(member.getId(), childId, scheduleId);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PatchMapping("/{childId}/schedules/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            LoginMember member,
            @PathVariable long childId,
            @PathVariable long scheduleId,
            @RequestBody @Valid UpdateScheduleRequest request
    ) {
        scheduleService.updateSchedule(member.getId(), childId, scheduleId, request.toContent());
        return ResponseEntity.ok().build();
    }
}
