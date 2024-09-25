package com.password926.agijagi.record.service;

import com.password926.agijagi.record.domain.*;
import com.password926.agijagi.record.domain.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final RecordReader recordReader;
    private final RecordAppender recordAppender;
    private final RecordRemover recordRemover;

    public List<Record> readRecord(
            long memberId,
            long childId,
            String type,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return recordReader.read(memberId, childId, type, startDate, endDate);
    }

    public List<Record> readLatestRecord(long memberId, long childId) {
        return recordReader.readLatest(memberId, childId);
    }

    public void appendRecord(
            long memberId,
            long childId,
            RecordContent recordContent
    ) {
        recordAppender.append(memberId, childId, recordContent);
    }

    public void removeRecord(long memberId, long recordId) {
        recordRemover.remove(memberId, recordId);
    }
}
