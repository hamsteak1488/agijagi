package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
@Component
public class ChildReader {

    private final ChildRepository childRepository;

    public Child read(long childId) {
        return childRepository.findByIdAndIsDeletedFalse(childId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    public int readMonthsOld(long childId) {
        Child child = read(childId);
        Period period = Period.between(child.getBirthday(), LocalDate.now());
        return period.getYears() * 12 + period.getMonths();
    }
}
