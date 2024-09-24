package com.password926.agijagi.schedule.controller.dto.request;

import com.password926.agijagi.schedule.domain.ScheduleContent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppendScheduleRequest {

    @NotNull
    private long childId;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    public ScheduleContent toContent() {
        return ScheduleContent.of(startDateTime, endDateTime, title, description);
    }
}
