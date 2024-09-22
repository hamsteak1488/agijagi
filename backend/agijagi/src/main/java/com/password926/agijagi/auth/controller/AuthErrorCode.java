package com.password926.agijagi.auth.controller;

import com.password926.agijagi.common.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
