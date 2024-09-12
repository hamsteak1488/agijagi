package com.password926.agijagi.child.service;

import com.password926.agijagi.child.domain.ChildDetail;
import com.password926.agijagi.child.domain.ChildDetailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildDetailReader childDetailReader;

    public ChildDetail readChildDetail(long memberId, long childId) {
        return childDetailReader.readByMemberAndChild(memberId, childId);
    }

    public List<ChildDetail> readChildDetailsByMember(long memberId) {
        return childDetailReader.readByMember(memberId);
    }
}
