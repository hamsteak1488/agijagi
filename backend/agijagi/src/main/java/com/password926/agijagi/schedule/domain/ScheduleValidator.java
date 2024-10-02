package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import org.springframework.stereotype.Component;

@Component
public class ScheduleValidator {

    public void validateOwner(long childId, Schedule schedule) {
        if (schedule.getChild().getId() != childId) {
            throw new RestApiException(CommonErrorCode.FORBIDDEN);
        }
    }
}
