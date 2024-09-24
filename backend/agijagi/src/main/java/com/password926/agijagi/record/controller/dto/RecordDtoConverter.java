package com.password926.agijagi.record.controller.dto;

import com.password926.agijagi.record.controller.dto.response.ReadRecordResponse;
import com.password926.agijagi.record.domain.Record;

import java.util.List;
import java.util.stream.Collectors;

public class RecordDtoConverter {

    public static List<ReadRecordResponse> convert(List<Record> records) {
        return records.stream()
                .map(ReadRecordResponse::from)
                .collect(Collectors.toList());
    }
}
