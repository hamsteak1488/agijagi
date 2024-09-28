package com.password926.agijagi.growth.service;

import com.password926.agijagi.growth.domain.GrowthAppender;
import com.password926.agijagi.growth.domain.GrowthContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GrowthService {

    private final GrowthAppender growthAppender;

    public void appendOrUpdateGrowth(
            long memberId,
            long childId,
            GrowthContent growthContent
    ) {
        growthAppender.appendOrUpdate(memberId, childId, growthContent);
    }
}
