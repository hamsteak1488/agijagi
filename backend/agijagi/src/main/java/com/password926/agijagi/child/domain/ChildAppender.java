package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.domain.MemberReader;
import com.password926.agijagi.milestone.domain.MilestoneStateAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ChildAppender {

    private final MediaStorage mediaStorage;
    private final ChildRepository childRepository;
    private final FollowerAppender followerAppender;
    private final MilestoneStateAppender milestoneStateAppender;

    @Transactional
    public void append(long memberId, ChildContent childContent, MultipartFile image) {
        Image storedImage = storeImageIfPresent(image);
        Child child = childRepository.save(Child.of(childContent, storedImage));
        followerAppender.append(memberId, child.getId(), Authority.WRITE);
        milestoneStateAppender.append(child);
    }

    private Image storeImageIfPresent(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        return mediaStorage.storeImage(MediaResource.from(image));
    }
}
