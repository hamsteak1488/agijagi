package com.password926.agijagi.record.controller.dto.response;

import com.password926.agijagi.record.domain.Record;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadRecordResponse {

    private Long id;

    private String type;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    public static ReadRecordResponse from(Record record) {
        return ReadRecordResponse.builder()
                .id(record.getId())
                .type(record.getType().getDesc())
                .startDateTime(record.getStartDateTime())
                .endDateTime(record.getEndDateTime())
                .build();
    }
}
