package com.password926.agijagi.record.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.record.infrastructure.RecordCustomRepository;
import com.password926.agijagi.record.infrastructure.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RecordReader {

    private final ChildValidator childValidator;
    private final RecordCustomRepository recordCustomRepository;
    private final RecordRepository recordRepository;

    public List<Record> read(
            long memberId,
            long childId,
            String type,
            LocalDate startDate,
            LocalDate endDate
    ) {
        childValidator.validateWriterRole(memberId, childId);
        return recordCustomRepository.findRecords(
                childId,
                type == null ? null : RecordType.of(type),
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX).withNano(0)
        );
    }

    public List<Record> readLatest(long memberId, long childId) {
        childValidator.validateWriterRole(memberId, childId);
        return Arrays.stream(RecordType.values())
                .map(type -> recordCustomRepository.findLatestRecord(childId, type))
                .filter(Objects::nonNull)
                .toList();
    }

    public Record read(long recordId) {
        return recordRepository.findById(recordId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
