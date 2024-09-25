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
        HashMap<String, Record> map = new HashMap<>();
        for (Record record : records) {
            map.put(record.getType().getDesc(), record);
        }

        List<ReadLatestRecordResponse> result = new ArrayList<>();
        for (RecordType recordType : RecordType.values()) {
            result.add(new ReadLatestRecordResponse(recordType.getDesc(), getDateTime(map.get(recordType.getDesc()))));
        }

        return result;
    }

    private static LocalDateTime getDateTime(Record record) {
        if (record == null) {
            return null;
        }
        if (record.getEndDateTime() != null) {
            return record.getEndDateTime();
        }
        return record.getStartDateTime();
    }
}
