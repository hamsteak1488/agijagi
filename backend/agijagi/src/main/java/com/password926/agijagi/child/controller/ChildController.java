package com.password926.agijagi.child.controller;

import com.password926.agijagi.child.domain.ChildDetail;
import com.password926.agijagi.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/child")
@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService childService;

    @GetMapping("/{childId}")
    public ResponseEntity<ChildDetail> readChildDetail(
            long memberId,
            @PathVariable long childId
    ) {
        return ResponseEntity.ok().body(childService.readChildDetail(memberId, childId));
    }

    @GetMapping
    public ResponseEntity<List<ChildDetail>> readChildDetails(long memberId) {
        return ResponseEntity.ok().body(childService.readChildDetailsByMember(memberId));
    }
}
