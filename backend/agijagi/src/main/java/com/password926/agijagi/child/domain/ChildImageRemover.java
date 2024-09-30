package com.password926.agijagi.child.domain;

import com.password926.agijagi.media.domain.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ChildImageRemover {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final MediaStorage mediaStorage;

    @Transactional
    public void remove(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        Child child = childReader.read(childId);
        mediaStorage.removeMedia(child.getImage().getId());
        child.updateImage(null);
    }
}
