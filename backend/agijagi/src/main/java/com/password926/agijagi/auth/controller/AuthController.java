package com.password926.agijagi.auth.controller;

import com.password926.agijagi.auth.controller.dto.request.LoginRequest;
import com.password926.agijagi.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpSession session) {
        long loginMemberId = authService.login(request.getEmail(), request.getPassword());
        session.setAttribute("loginMemberId", loginMemberId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/whoami")
    public ResponseEntity<Long> whoami(HttpSession session) {
        long loginMemberId = (long) session.getAttribute("loginMemberId");
        System.out.println("loginMemberId = " + loginMemberId);

        return ResponseEntity.ok(loginMemberId);
    }
}
