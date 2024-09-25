package com.password926.agijagi.record.infrastructure;

import com.password926.agijagi.record.domain.Record;
import com.password926.agijagi.record.domain.RecordType;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordCustomRepository {

    List<Record> findRecords(long childId, RecordType type, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Record findLatestRecord(long childId, RecordType type);
}
