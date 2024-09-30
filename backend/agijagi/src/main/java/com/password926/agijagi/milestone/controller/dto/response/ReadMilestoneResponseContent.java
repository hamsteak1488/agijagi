package com.password926.agijagi.milestone.controller.dto.response;

import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReadMilestoneResponseContent {

    private long id;

    private String content;

    private int requiredAmount;

    private int currentAmount;

    private LocalDate date;

    public static ReadMilestoneResponseContent from(MilestoneStateDetail milestoneStateDetail) {
        return ReadMilestoneResponseContent.builder()
                .id(milestoneStateDetail.getId())
                .content(milestoneStateDetail.getContent())
                .requiredAmount(milestoneStateDetail.getRequiredAmount())
                .currentAmount(milestoneStateDetail.getCurrentAmount())
                .date(milestoneStateDetail.getDate())
                .build();
    }
}
