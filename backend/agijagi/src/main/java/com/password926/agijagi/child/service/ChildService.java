package com.password926.agijagi.child.service;

import com.password926.agijagi.child.domain.ChildFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildFinder childFinder;

    public void findChildren(long memberId) {
        Member member = MemberFinder.find(memberId);
        childFinder.find(member);

    }
}
