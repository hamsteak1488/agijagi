package com.password926.agijagi.post.domain;

import com.password926.agijagi.post.infrastructure.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentDetailReader {
    private final CommentReader commentReader;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public CommentDetail read(long commentId) {
        Comment comment = commentReader.read(commentId);

        return CommentDetail.builder()
                .commentId(commentId)
                .writerId(comment.getWriter().getId())
                .writerNickname(comment.getWriter().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CommentDetail> readByPostId(long postId) {
        return commentRepository.findByPostIdMemberFetch(postId).stream()
                .map(comment -> CommentDetail.builder()
                        .commentId(comment.getId())
                        .writerId(comment.getWriter().getId())
                        .writerNickname(comment.getWriter().getNickname())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .toList();
    }
}
