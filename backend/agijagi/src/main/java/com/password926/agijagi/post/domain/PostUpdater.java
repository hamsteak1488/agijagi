package com.password926.agijagi.post.domain;

import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostUpdater {
    private final PostReader postReader;
    private final MediaStorage mediaStorage;

    @Transactional
    public void updatePost(long postId, String title, String content, List<MediaResource> newMediaList, List<UUID> removeMediaList) {
        Post post = postReader.read(postId);
        post.update(title, content);
        newMediaList.forEach(m -> {
            post.addMedia(mediaStorage.storeAny(m));
        });
        removeMediaList.forEach(post::removeMedia);
    }
}
