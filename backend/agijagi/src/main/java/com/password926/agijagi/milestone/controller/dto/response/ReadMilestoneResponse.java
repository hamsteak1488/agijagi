package com.password926.agijagi.milestone.controller.dto.response;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ReadMilestoneResponse {

    private String title;

    private List<ReadMilestoneResponseContent> milestones;
}
