package com.password926.agijagi.milestone.controller.dto;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponse;
import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponseContent;
import com.password926.agijagi.milestone.domain.MilestoneStateDetail;

import java.util.ArrayList;
import java.util.List;

public class MilestoneDtoConverter {

    public static List<ReadMilestoneResponse> convert(List<MilestoneStateDetail> milestones) {
        List<ReadMilestoneResponse> response = new ArrayList<>();
        if (milestones.isEmpty()) {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
        String title = milestones.get(0).getTitle();
        List<ReadMilestoneResponseContent> content = new ArrayList<>();
        content.add(ReadMilestoneResponseContent.from(milestones.get(0)));

        for (int i = 1; i < milestones.size(); i++) {
            MilestoneStateDetail milestone = milestones.get(i);
            String currTitle = milestone.getTitle();

            if (title.equals(currTitle)) {
                content.add(ReadMilestoneResponseContent.from(milestone));
            } else {
                response.add(new ReadMilestoneResponse(title, content));
                title = milestone.getTitle();
                content = new ArrayList<>();
                content.add(ReadMilestoneResponseContent.from(milestone));
            }
        }
        response.add(new ReadMilestoneResponse(title, content));

        return response;
    }
}
