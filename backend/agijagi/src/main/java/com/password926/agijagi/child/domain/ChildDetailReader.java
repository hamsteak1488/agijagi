package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChildDetailReader {

    private final MemberChildReader memberChildReader;
    private final ChildReader childReader;
    private final FollowerReader followerReader;

    @Transactional(readOnly = true)
    public ChildDetail readByMemberAndChild(long memberId, long childId) {
        Child child = childReader.read(childId);
        MemberChild memberChild = memberChildReader.readByMemberAndChild(memberId, childId);
        long followerNum = followerReader.readFollowerNum(childId);

        return ChildDetail.of(child, memberChild.getAuthority(), followerNum);
    }

    @Transactional(readOnly = true)
    public List<ChildDetail> readByMember(long memberId) {
        List<MemberChild> memberChilds = memberChildReader.readByMember(memberId);

        return memberChilds.stream()
                .map(memberChild -> {
                    Child child = childReader.read(memberChild.readChildId());
                    long followerNum = followerReader.readFollowerNum(child.getId());
                    return ChildDetail.of(childReader.read(memberChild.readChildId()), memberChild.getAuthority(), followerNum);
                })
                .toList();
    }
}
