package com.password926.agijagi.growth.domain;

import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.growth.infrastructure.GrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GrowthReader {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final GrowthRepository growthRepository;

    @Transactional(readOnly = true)
    public List<Growth> readAll(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        int monthsOld = childReader.readMonthsOld(childId);
        return growthRepository.findAllByChildIdAndMonthLessThanEqualOrderByMonth(childId, monthsOld);
    }

    public List<Growth> readAllByMonth(long childId, int month) {
        return growthRepository.findAllByChildIdAndMonthLessThanEqualOrderByMonth(childId, month);
    }
}
