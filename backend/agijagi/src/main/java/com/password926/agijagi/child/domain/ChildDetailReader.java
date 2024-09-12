package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.MemberChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ChildDetailReader {

    private final MemberChildReader memberChildReader;
    private final ChildReader childReader;
    private final MemberChildRepository memberChildRepository;

    public ChildDetail readByMemberAndChild(long memberId, long childId) {
        Child child = childReader.read(childId);
        MemberChild memberChild = memberChildReader.readByMemberAndChild(memberId, childId);
        long followerNum = memberChildRepository.countByChildId(childId);

        return ChildDetail.of(child, memberChild.getRole(), followerNum);
    }

    public List<ChildDetail> readByMember(long memberId) {
        List<MemberChild> memberChilds = memberChildReader.readByMember(memberId);

        return memberChilds.stream()
                .map(memberChild -> {
                    Child child = childReader.read(memberChild.getChildId());
                    long followerNum = memberChildReader.readFollowerNum(child.getId());
                    return ChildDetail.of(childReader.read(memberChild.getChildId()), memberChild.getRole(), followerNum);
                })
                .collect(Collectors.toList());
    }
}
