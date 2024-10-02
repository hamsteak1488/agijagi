package com.password926.agijagi.growth.domain;

import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.growth.infrastructure.GrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class GrowthAppender {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final GrowthRepository growthRepository;

    // TODO: 개월을 클라로 부터 현재 받고 있는데, 안받고 백에서 처리할지 결정해야함
    @Transactional
    public void appendOrUpdate(long memberId, long childId, GrowthContent growthContent) {
        childValidator.validateWriteAuthority(memberId, childId);
        growthRepository.findByChildIdAndMonth(childId, growthContent.getMonth())
                .ifPresentOrElse(
                        growth -> growth.update(growthContent),
                        () -> append(childId, growthContent)
                );
    }

    private void append(long childId, GrowthContent growthContent) {
        Growth growth = Growth.builder()
                .child(childReader.read(childId))
                .weight(growthContent.getWeight())
                .height(growthContent.getHeight())
                .month(growthContent.getMonth())
                .build();
        growthRepository.save(growth);
    }
}
