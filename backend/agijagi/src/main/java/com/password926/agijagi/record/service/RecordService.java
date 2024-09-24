package com.password926.agijagi.record.service;

import com.password926.agijagi.record.domain.Record;
import com.password926.agijagi.record.domain.RecordAppender;
import com.password926.agijagi.record.domain.RecordContent;
import com.password926.agijagi.record.domain.RecordReader;
import com.password926.agijagi.record.domain.RecordRemover;
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

    public List<Record> readRecordByDates(
            long memberId,
            long childId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return recordReader.readByDates(memberId, childId, startDate, endDate);
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
