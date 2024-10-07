package com.password926.agijagi.post.domain;

import com.password926.agijagi.media.domain.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMediaReader {
    private final PostReader postReader;

    @Transactional(readOnly = true)
    public List<String> readPostMediaUrls(long postId) {
        Post post = postReader.read(postId);

        return post.getPostMediaList().stream()
                .sorted(Comparator.comparingInt(PostMedia::getMediaOrder))
                .map(PostMedia::getMedia)
                .map(Media::getUrl)
                .toList();
    }
}
