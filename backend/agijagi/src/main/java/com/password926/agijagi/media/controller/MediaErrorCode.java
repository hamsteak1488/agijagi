package com.password926.agijagi.media.controller;

import com.password926.agijagi.common.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MediaErrorCode implements ErrorCode {
    UNKNOWN_MEDIA_TYPE(HttpStatus.BAD_REQUEST, "미디어 타입을 알 수 없습니다."),
    INVALID_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 이미지 타입입니다."),
    INVALID_VIDEO_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 비디오 타입입니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
