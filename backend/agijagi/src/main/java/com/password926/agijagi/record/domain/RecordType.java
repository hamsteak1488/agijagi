package com.password926.agijagi.record.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RecordType {

    FECES("대변"),
    PISS("소변"),
    SLEEP("수면"),
    FOOD("식사"),
    PUMP("유축"),
    MEDICINE("약"),
    ;

    private final String desc;

    public static RecordType of(String desc) {
        return Arrays.stream(RecordType.values())
                .filter(v -> v.getDesc().equals(desc))
                .findAny()
                .orElseThrow(() -> new RestApiException(CommonErrorCode.INVALID_PARAMETER));
    }
}
