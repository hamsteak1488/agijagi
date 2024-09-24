package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.schedule.infrastructure.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleReader {

    private final ChildValidator childValidator;
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> readByDates(
            long memberId,
            long childId,
            LocalDate starDate,
            LocalDate endDate
    ) {
        childValidator.validateWriterRole(memberId, childId);
        return scheduleRepository.findAllByChildIdAndStartDateTimeBetween(
                childId,
                starDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX).withNano(0)
        );
    }

    public Schedule read(long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
