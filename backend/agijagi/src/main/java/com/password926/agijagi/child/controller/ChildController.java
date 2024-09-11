package com.password926.agijagi.child.controller;

import com.password926.agijagi.child.domain.ChildDetail;
import com.password926.agijagi.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService childService;

    @GetMapping("/child")
    public ResponseEntity<List<ChildDetail>> readChildDetail(long memberId) {
        return ResponseEntity.ok().body(childService.readChildDetail(memberId));
    }
}
