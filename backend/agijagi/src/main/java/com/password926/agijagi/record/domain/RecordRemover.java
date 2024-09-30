package com.password926.agijagi.record.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.record.infrastructure.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class RecordRemover {

    private final RecordReader recordReader;
    private final ChildValidator childValidator;
    private final RecordValidator recordValidator;
    private final RecordRepository recordRepository;

    @Transactional
    public void remove(long memberId, long childId, long recordId) {
        Record record = recordReader.read(recordId);
        childValidator.validateWriteAuthority(memberId, record.getChild().getId());
        recordValidator.validateOwner(childId, record.getChild().getId());
        recordRepository.delete(record);
    }
}
