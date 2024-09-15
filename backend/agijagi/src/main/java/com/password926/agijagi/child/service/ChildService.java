package com.password926.agijagi.child.service;

import com.password926.agijagi.child.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildDetailReader childDetailReader;
    private final ChildAppender childAppender;
    private final ChildRemover childRemover;

    public ChildDetail readChildDetail(long memberId, long childId) {
        return childDetailReader.readByMemberAndChild(memberId, childId);
    }

    public List<ChildDetail> readChildDetailsByMember(long memberId) {
        return childDetailReader.readByMember(memberId);
    }

    public void appendChild(
            long memberId,
            ChildContent childContent,
            MultipartFile image
    ) {
        childAppender.append(memberId, childContent, image);
    }

    public void removeChild(long memberId, long childId) {
        childRemover.remove(memberId, childId);
    }
}
