package com.password926.agijagi.child.controller;

import com.password926.agijagi.common.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChildErrorCode implements ErrorCode {
    EXISTING_RELATION(HttpStatus.CONFLICT, "이미 존재하는 관계입니다."),
    UPDATE_SELF_AUTHORITY(HttpStatus.FORBIDDEN, "자기 자신의 권한은 수정할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
