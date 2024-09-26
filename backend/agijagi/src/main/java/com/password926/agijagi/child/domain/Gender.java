package com.password926.agijagi.child.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("남아"),
    FEMALE("여아"),
    UNKNOWN("모름")
    ;

    private final String desc;

    public static Gender of(String desc) {
        return Arrays.stream(Gender.values())
                .filter(v -> v.getDesc().equals(desc))
                .findAny()
                .orElseThrow(() -> new RestApiException(CommonErrorCode.INVALID_PARAMETER));
    }
}
