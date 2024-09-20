package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChildReader {

    private final ChildRepository childRepository;

    public Child read(long childId) {
        return childRepository.findById(childId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
