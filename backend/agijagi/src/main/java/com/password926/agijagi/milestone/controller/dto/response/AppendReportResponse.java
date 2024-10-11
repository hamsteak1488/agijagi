package com.password926.agijagi.milestone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppendReportResponse {

    private long id;

    public static AppendReportResponse of(long id) {
        return new AppendReportResponse(id);
    }
}
