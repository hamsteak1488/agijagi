package com.password926.agijagi.post.domain;

import com.password926.agijagi.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isDeleted;

    public static Comment createComment(Member writer, Post post, String content) {
        Comment comment = new Comment();
        comment.writer = writer;
        comment.post = post;
        comment.content = content;

        comment.createdAt = LocalDateTime.now();
        comment.isDeleted = false;

        return comment;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        isDeleted = true;
    }
}
