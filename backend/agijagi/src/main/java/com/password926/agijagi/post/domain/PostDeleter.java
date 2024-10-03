package com.password926.agijagi.post.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostDeleter {
    private final PostReader postReader;

    @Transactional
    public void deletePost(long postId) {
        Post post = postReader.read(postId);
        post.delete();
    }
}
