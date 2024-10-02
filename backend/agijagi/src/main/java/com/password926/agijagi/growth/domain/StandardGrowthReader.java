package com.password926.agijagi.growth.domain;

import com.password926.agijagi.child.domain.Gender;
import com.password926.agijagi.growth.infrastructure.StandardGrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class StandardGrowthReader {

    private final StandardGrowthRepository standardGrowthRepository;

    public List<StandardGrowth> read(int month, Gender gender) {
        return standardGrowthRepository.findAllByMonthLessThanAndGender(month, gender);
    }
}
