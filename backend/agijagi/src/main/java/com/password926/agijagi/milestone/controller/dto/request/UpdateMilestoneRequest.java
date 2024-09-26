package com.password926.agijagi.milestone.controller.dto.request;

import com.password926.agijagi.milestone.domain.MilestoneStateContent;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UpdateMilestoneRequest {

    @Size(min = 1)
    private List<RequestContent> milestones;

    public List<MilestoneStateContent> toContents() {
        return milestones.stream()
                .map(RequestContent::toContent)
                .collect(Collectors.toList());
    }

    @Getter
    public static class RequestContent {
        private long id;
        private int currentAmount;

        private MilestoneStateContent toContent() {
            return new MilestoneStateContent(this.id, this.currentAmount);
        }
    }
}
