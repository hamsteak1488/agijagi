package com.password926.agijagi.growth.domain;

import com.password926.agijagi.growth.infrastructure.GrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GrowthReader {

    private final GrowthRepository growthRepository;

    public List<Growth> readAllByMonth(long childId, int month) {
        return growthRepository.findAllByChildIdAndMonthLessThan(childId, month);
    }
}
