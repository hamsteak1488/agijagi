package com.password926.agijagi.milestone.controller;

import com.password926.agijagi.milestone.controller.dto.MilestoneDtoConverter;
import com.password926.agijagi.milestone.controller.dto.request.CreateMilestoneAnalysisRequest;
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

    @GetMapping("/{childId}/milestone")
    public ResponseEntity<List<ReadMilestoneResponse>> readMilestone(
            long memberId,
            @PathVariable long childId,
            @RequestParam int month
    ) {
        return ResponseEntity.ok().body(MilestoneDtoConverter.convert(milestoneService.readMilestone(memberId, childId, month)));
    }

    @PatchMapping("/{childId}/milestone")
    public ResponseEntity<Void> updateMilestone(
            long memberId,
            @PathVariable long childId,
            @RequestBody @Valid UpdateMilestoneRequest request
    ) {
        milestoneService.updateMilestone(memberId, childId, request.toContents());
        return ResponseEntity.ok().build();
    }
}
