package com.password926.agijagi.post.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentDeleter {
    private final CommentReader commentReader;

    @Transactional
    public void delete(long commentId) {
        Comment comment = commentReader.read(commentId);
        comment.delete();
    }
}
