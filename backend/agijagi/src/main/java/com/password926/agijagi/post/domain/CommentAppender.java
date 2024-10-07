package com.password926.agijagi.post.domain;

import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.domain.MemberReader;
import com.password926.agijagi.post.infrastructure.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentAppender {
    private final CommentRepository commentRepository;
    private final MemberReader memberReader;
    private final PostReader postReader;
    private final CommentReader commentReader;

    @Transactional
    public long append(long writerId, long postId, Long parentCommentId, String content) {
        Member writer = memberReader.read(writerId);
        Post post = postReader.read(postId);
        Comment parentComment = parentCommentId != null ? commentReader.read(parentCommentId) : null;
        Comment comment = Comment.createComment(writer, post, parentComment, content);

        commentRepository.save(comment);

        return comment.getId();
    }
}
