package com.password926.agijagi.child.controller;

import com.password926.agijagi.child.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChildController {

    private final ChildService childService;

    @GetMapping("/child")
    public ResponseEntity<Void> findChildren() {
        return ResponseEntity.ok().build();
    }
}
