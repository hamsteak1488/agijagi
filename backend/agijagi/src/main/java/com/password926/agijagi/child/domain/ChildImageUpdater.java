package com.password926.agijagi.child.domain;

import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ChildImageUpdater {

    private final ChildValidator childValidator;
    private final MediaStorage mediaStorage;
    private final ChildReader childReader;

    @Transactional
    public void update(long memberId, long childId, MultipartFile image) {
        childValidator.validateWriteAuthority(memberId, childId);
        Image storeImage = mediaStorage.storeImage(image.getResource(), image.getContentType());
        childReader.read(childId).updateImage(storeImage);
    }
}
