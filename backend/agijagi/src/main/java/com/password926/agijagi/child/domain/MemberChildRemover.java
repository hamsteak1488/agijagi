package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberChildRemover {

    private final MemberChildRepository memberChildRepository;

    public void removeAllRelation(long childId) {

        memberChildRepository.deleteAllByChildId(childId);
    }
}
