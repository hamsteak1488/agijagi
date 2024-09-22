package com.password926.agijagi.auth.service;

import com.password926.agijagi.auth.controller.domain.LoginValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final LoginValidator loginValidator;

    public long login(String email, String password) {
        return loginValidator.validate(email, password);
    }
}
