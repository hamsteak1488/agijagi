package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MemberChildReader {

    private final MemberChildRepository memberChildRepository;

    public List<MemberChild> read(long memberId) {
        return memberChildRepository.findByMemberId(memberId);
    }

    public long readFollowerNum(long childId) {
        return memberChildRepository.countByChildId(childId);
    }
}
