package com.password926.agijagi.milestone.controller.dto.response;

import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReadMilestoneResponse {

    private String title;

    private List<ResponseContent> milestones;

    @Getter
    @Builder
    public static class ResponseContent {
        private long id;
        private String content;
        private int requiredAmount;
        private int currentAmount;
        private LocalDate date;

        public static ResponseContent from(MilestoneStateDetail milestoneStateDetail) {
            return ResponseContent.builder()
                    .id(milestoneStateDetail.getId())
                    .content(milestoneStateDetail.getContent())
                    .requiredAmount(milestoneStateDetail.getRequiredAmount())
                    .currentAmount(milestoneStateDetail.getCurrentAmount())
                    .date(milestoneStateDetail.getDate())
                    .build();
        }
    }
}
