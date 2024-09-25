package com.password926.agijagi.record.domain;

import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.record.infrastructure.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RecordAppender {

    private final ChildValidator childValidator;
    private final RecordRepository recordRepository;
    private final ChildReader childReader;

    public void append(
            long memberId,
            long childId,
            RecordContent recordContent
    ) {
        childValidator.validateWriterRole(memberId, childId);
        recordRepository.save(Record.builder()
                .child(childReader.read(childId))
                .type(recordContent.getType())
                .startDateTime(recordContent.getStartDateTime())
                .endDateTime(recordContent.getEndDateTime())
                .createAt(LocalDateTime.now())
                .build()
        );
    }
}
