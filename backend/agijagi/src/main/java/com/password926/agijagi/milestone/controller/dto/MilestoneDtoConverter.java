package com.password926.agijagi.milestone.controller.dto;

import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponse;
import com.password926.agijagi.milestone.controller.dto.response.ReadMilestoneResponseContent;
import com.password926.agijagi.milestone.domain.MilestoneStateDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MilestoneDtoConverter {

    public static List<ReadMilestoneResponse> convert(List<MilestoneStateDetail> milestones) {
        HashMap<String, List<ReadMilestoneResponseContent>> map = new HashMap<>();

        for (MilestoneStateDetail ms : milestones) {
            String title = ms.getTitle();
            if (!map.containsKey(title)) {
                map.put(title, new ArrayList<>());
            }
            map.get(title).add(ReadMilestoneResponseContent.from(ms));
        }

        List<ReadMilestoneResponse> response = new ArrayList<>();
        for (String title : map.keySet()) {
            List<ReadMilestoneResponseContent> value = map.get(title);
            response.add(new ReadMilestoneResponse(title, value));
        }

        return response;
    }
}
