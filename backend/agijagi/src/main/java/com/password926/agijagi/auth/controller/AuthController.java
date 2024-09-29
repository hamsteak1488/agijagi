package com.password926.agijagi.auth.controller;

import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.auth.controller.dto.request.LoginRequest;
import com.password926.agijagi.auth.service.AuthService;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
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
        session.setAttribute(AuthConstants.SESSION_LOGIN_MEMBER_KEY, loginMemberId);

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @GetMapping("/whoami")
    public ResponseEntity<LoginMember> whoami(LoginMember loginMember) {
        System.out.println("loginMember = " + loginMember);

        return ResponseEntity.ok(loginMember);
    }

    @RequestMapping("/error/unauthenticated")
    public ResponseEntity<Void> error(HttpServletRequest request) {
        throw new RestApiException(CommonErrorCode.UNAUTHORIZED);
    }
}
