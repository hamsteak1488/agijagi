package com.password926.agijagi.record.service;

import com.password926.agijagi.record.domain.RecordAppender;
import com.password926.agijagi.record.domain.RecordContent;
import com.password926.agijagi.record.domain.RecordRemover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final RecordAppender recordAppender;
    private final RecordRemover recordRemover;

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
