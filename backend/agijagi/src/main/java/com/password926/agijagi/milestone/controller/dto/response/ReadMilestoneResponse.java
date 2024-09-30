package com.password926.agijagi.milestone.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReadMilestoneResponse {

    private String title;

    private List<ReadMilestoneResponseContent> milestones;
}
