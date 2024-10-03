package com.password926.agijagi.post.domain;

import com.password926.agijagi.media.domain.Media;
import com.password926.agijagi.media.domain.MediaReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMediaReader {

    private final PostReader postReader;
    private final MediaReader mediaReader;

    @Transactional(readOnly = true)
    public List<String> readPostMediaUrls(long postId) {
        Post post = postReader.read(postId);

        return mediaReader.getMediaUrls(post.getPostMediaList().stream()
                .map(PostMedia::getMedia)
                .map(Media::getId)
                .toList());
    }
}
