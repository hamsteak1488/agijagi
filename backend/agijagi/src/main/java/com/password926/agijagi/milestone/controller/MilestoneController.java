package com.password926.agijagi.milestone.controller;

import com.password926.agijagi.milestone.controller.dto.MilestoneDtoConverter;
import com.password926.agijagi.milestone.controller.dto.request.UpdateMilestoneRequest;
import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponse;
import com.password926.agijagi.milestone.service.MilestoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/milestones")
@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping
    public ResponseEntity<List<ReadMilestoneResponse>> readMilestone(
            long memberId,
            @RequestParam long childId,
            @RequestParam int month
    ) {
        return ResponseEntity.ok().body(MilestoneDtoConverter.convert(milestoneService.readMilestone(memberId, childId, month)));
    }

    @PatchMapping
    public ResponseEntity<Void> updateMilestone(
            long memberId,
            @RequestBody @Valid UpdateMilestoneRequest request
    ) {
        milestoneService.updateMilestone(memberId, request.getChildId(), request.toContents());
        return ResponseEntity.ok().build();
    }
}
