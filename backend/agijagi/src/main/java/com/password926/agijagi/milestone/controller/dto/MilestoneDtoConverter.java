package com.password926.agijagi.milestone.controller.dto;

import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponse;
import com.password926.agijagi.milestone.domain.MilestoneStateDetail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MilestoneDtoConverter {

    public static List<ReadMilestoneResponse> convert(List<MilestoneStateDetail> milestones) {
        LinkedHashMap<String, List<ReadMilestoneResponse.ResponseContent>> map = new LinkedHashMap<>();

        for (MilestoneStateDetail ms : milestones) {
            String title = ms.getTitle();
            if (!map.containsKey(title)) {
                map.put(title, new ArrayList<>());
            }
            map.get(title).add(ReadMilestoneResponse.ResponseContent.from(ms));
        }

        List<ReadMilestoneResponse> response = new ArrayList<>();
        for (String title : map.keySet()) {
            List<ReadMilestoneResponse.ResponseContent> value = map.get(title);
            response.add(new ReadMilestoneResponse(title, value));
        }

        return response;
    }
}
