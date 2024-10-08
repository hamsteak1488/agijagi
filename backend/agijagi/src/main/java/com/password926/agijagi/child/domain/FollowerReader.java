package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.MemberChildCustomRepository;
import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FollowerReader {

    private final ChildValidator childValidator;
    private final MemberChildCustomRepository memberChildCustomRepository;
    private final MemberChildRepository memberChildRepository;

    public List<Follower> readFollowers(long memberId, long childId) {
        childValidator.validateReadAuthority(memberId, childId);
        return memberChildCustomRepository.findFollowers(childId);
    }

    public long readFollowerNum(long childId) {
        return memberChildRepository.countByChildId(childId);
    }
}
