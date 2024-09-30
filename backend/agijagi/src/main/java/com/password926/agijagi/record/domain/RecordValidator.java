package com.password926.agijagi.record.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import org.springframework.stereotype.Component;

@Component
public class RecordValidator {

    public void validateOwner(long childId, long recordId) {
        if (childId != recordId) {
            throw new RestApiException(CommonErrorCode.FORBIDDEN);
        }
    }
}
