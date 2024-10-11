package com.password926.agijagi.member.controller;

import com.password926.agijagi.common.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 가진 회원을 찾을 수 없습니다."),
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "해당 이메일을 가진 회원이 이미 존재합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
