package com.password926.agijagi.record.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReadLatestRecordResponse {

    private String type;

    private LocalDateTime latestDateTime;
}
