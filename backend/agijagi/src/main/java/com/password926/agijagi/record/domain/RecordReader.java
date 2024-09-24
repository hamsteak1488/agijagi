package com.password926.agijagi.record.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.record.infrastructure.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RecordReader {

    private final ChildValidator childValidator;
    private final RecordRepository recordRepository;

    public List<Record> readByDates(
            long memberId,
            long childId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        childValidator.validateWriterRole(memberId, childId);
        return recordRepository.findAllByChildIdAndDateTimeBetween(
                childId,
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX).withNano(0)
        );
    }

    public Record read(long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
