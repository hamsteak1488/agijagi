package com.password926.agijagi.media.controller;

import com.password926.agijagi.common.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MediaErrorCode implements ErrorCode {
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
