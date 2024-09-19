package com.password926.agijagi.milestone.controller;

import com.password926.agijagi.milestone.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/milestone")
@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping()
    public ResponseEntity<Void> readMilestone(
            long memberId,
            @RequestParam(required = true) long childId,
            @RequestParam(required = true) int mont
    ) {
        return ResponseEntity.ok().build();
    }
}
