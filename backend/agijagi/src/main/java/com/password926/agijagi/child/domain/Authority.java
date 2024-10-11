package com.password926.agijagi.child.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Authority {

    WRITE,
    READ,
    ;

    public boolean isWriteAuthority() {
        return this == WRITE;
    }

    public boolean isReadAuthority() {
        return this == READ || this == WRITE;
    }

    public static Authority of(String authority) {
        return Arrays.stream(Authority.values())
                .filter(v -> v.name().equals(authority))
                .findAny()
                .orElseThrow(() -> new RestApiException(CommonErrorCode.INVALID_PARAMETER));
    }
}
