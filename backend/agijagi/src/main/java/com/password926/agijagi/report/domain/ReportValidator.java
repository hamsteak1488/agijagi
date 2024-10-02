package com.password926.agijagi.report.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import org.springframework.stereotype.Component;


@Component
public class ReportValidator {

    public void validateOwner(long childId, Report report) {
        if (report.getChildId() != childId) {
            throw new RestApiException(CommonErrorCode.FORBIDDEN);
        }
    }
}
