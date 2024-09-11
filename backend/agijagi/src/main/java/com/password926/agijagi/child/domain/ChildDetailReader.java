package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ChildDetailReader {

    private final MemberChildReader memberChildReader;
    private final ChildReader childReader;

    public List<ChildDetail> read(long memberId) {
        List<MemberChild> memberChilds = memberChildReader.read(memberId);

        return memberChilds.stream()
                .map(memberChild -> {
                    Child child = childReader.read(memberChild.getChildId());
                    long followerNum = memberChildReader.readFollowerNum(child.getId());
                    return ChildDetail.of(childReader.read(memberChild.getChildId()), memberChild.getRole(), followerNum);
                })
                .collect(Collectors.toList());
    }
}
