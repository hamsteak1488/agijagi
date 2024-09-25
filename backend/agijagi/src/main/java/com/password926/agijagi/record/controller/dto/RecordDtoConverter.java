package com.password926.agijagi.record.controller.dto;

import com.password926.agijagi.record.controller.dto.response.ReadLatestRecordResponse;
import com.password926.agijagi.record.controller.dto.response.ReadRecordResponse;
import com.password926.agijagi.record.domain.Record;
import com.password926.agijagi.record.domain.RecordType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RecordDtoConverter {

    public static List<ReadRecordResponse> convert(List<Record> records) {
        return records.stream()
                .map(ReadRecordResponse::from)
                .collect(Collectors.toList());
    }

    public static List<ReadLatestRecordResponse> convertToLatest(List<Record> records) {
        Map<String, Record> map = records.stream()
                .collect(Collectors.toMap(record -> record.getType().getDesc(), record -> record));

        return Arrays.stream(RecordType.values())
                .map(recordType -> new ReadLatestRecordResponse(recordType.getDesc(), getLatestDateTime(map.get(recordType.getDesc()))))
                .collect(Collectors.toList());
    }

    private static LocalDateTime getLatestDateTime(Record record) {
        if (record == null) {
            return null;
        }
        if (record.getEndDateTime() != null) {
            return record.getEndDateTime();
        }
        return record.getStartDateTime();
    }
}
