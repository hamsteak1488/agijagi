package com.password926.agijagi.post.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentUpdater {
    private final CommentReader commentReader;

    @Transactional
    public void updateComment(long commentId, String content) {
        Comment comment = commentReader.read(commentId);
        comment.updateContent(content);
    }
}
