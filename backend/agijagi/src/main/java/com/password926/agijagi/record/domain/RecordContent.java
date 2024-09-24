package com.password926.agijagi.record.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class RecordContent {

    private final RecordType type;

    private final LocalDateTime dateTime;
}
