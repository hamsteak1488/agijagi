package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.milestone.infrastructure.MilestoneStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MilestoneStateReader {

    private final MilestoneStateRepository milestoneStateRepository;

    public MilestoneState read(long milestoneStateId) {
        return milestoneStateRepository.findMilestoneById(milestoneStateId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
