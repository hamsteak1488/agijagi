package com.password926.agijagi.growth.service;

import com.password926.agijagi.growth.domain.Growth;
import com.password926.agijagi.growth.domain.GrowthAppender;
import com.password926.agijagi.growth.domain.GrowthContent;
import com.password926.agijagi.growth.domain.GrowthReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GrowthService {

    private final GrowthReader growthReader;
    private final GrowthAppender growthAppender;

    public List<Growth> readGrowth(long memberId, long childId) {
        return growthReader.readAll(memberId, childId);
    }


    public void appendOrUpdateGrowth(
            long memberId,
            long childId,
            GrowthContent growthContent
    ) {
        growthAppender.appendOrUpdate(memberId, childId, growthContent);
    }
}
