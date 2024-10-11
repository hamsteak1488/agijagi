package com.password926.agijagi.record.controller.dto.request;

import com.password926.agijagi.record.domain.RecordContent;
import com.password926.agijagi.record.domain.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppendRecordRequest {

    @NotBlank
    private String type;

    @NotNull
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    public RecordContent toContent() {
        return new RecordContent(RecordType.of(type), startDateTime, endDateTime);
    }
}
