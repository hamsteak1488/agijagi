package com.password926.agijagi.post.service;

import com.password926.agijagi.post.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentAppender commentAppender;
    private final CommentUpdater commentUpdater;
    private final CommentDeleter commentDeleter;
    private final CommentDetailReader commentDetailReader;
    private final CommentAuthorityValidator commentAuthorityValidator;

    public long createComment(long requesterId, long postId, Long parentCommentId, String content) {
        return commentAppender.append(requesterId, postId, parentCommentId, content);
    }

    public void updateComment(long requesterId, long commentId, String content) {
        commentAuthorityValidator.validate(requesterId, commentId);

        commentUpdater.updateComment(commentId, content);
    }

    public void deleteComment(long requesterId, long commentId) {
        commentAuthorityValidator.validate(requesterId, commentId);

        commentDeleter.delete(commentId);
    }

    public List<CommentDetail> getCommentDetails(long postId) {
        return commentDetailReader.readByPostId(postId);
    }
}
