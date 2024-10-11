package com.password926.agijagi.milestone.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.milestone.controller.dto.MilestoneDtoConverter;
import com.password926.agijagi.milestone.controller.dto.request.UpdateMilestoneRequest;
import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponse;
import com.password926.agijagi.milestone.service.MilestoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/children")
@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @Authenticate
    @GetMapping("/{childId}/milestones")
    public ResponseEntity<List<ReadMilestoneResponse>> readMilestone(
            LoginMember member,
            @PathVariable long childId,
            @RequestParam int month
    ) {
        return ResponseEntity.ok().body(MilestoneDtoConverter.convert(milestoneService.readMilestone(member.getId(), childId, month)));
    }

    @Authenticate
    @PatchMapping("/{childId}/milestones")
    public ResponseEntity<Void> updateMilestone(
            LoginMember member,
            @PathVariable long childId,
            @RequestBody @Valid UpdateMilestoneRequest request
    ) {
        milestoneService.updateMilestone(member.getId(), childId, request.toContents());
        return ResponseEntity.ok().build();
    }
}
