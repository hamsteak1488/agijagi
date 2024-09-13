package com.password926.agijagi.child.controller;

import com.password926.agijagi.child.controller.request.AppendChildRequest;
import com.password926.agijagi.child.domain.ChildDetail;
import com.password926.agijagi.child.service.ChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> appendChild(
            long memberId,
            @Valid AppendChildRequest appendChildRequest
    ) {

        return ResponseEntity.ok().build();
    }
}
