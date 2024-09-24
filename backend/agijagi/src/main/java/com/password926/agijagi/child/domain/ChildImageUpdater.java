package com.password926.agijagi.child.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ChildImageUpdater {

    private final ChildValidator childValidator;
    private final ChildReader childReader;

    @Transactional
    public void update(long memberId, long childId, MultipartFile image) {
        childValidator.validateWriterRole(memberId, childId);
        //TODO: s3 구현시 수정
        String imageUrl = null;
        childReader.read(childId).updateImage(imageUrl);
    }
}
