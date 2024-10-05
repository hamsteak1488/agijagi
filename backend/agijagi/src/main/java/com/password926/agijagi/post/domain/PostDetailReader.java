package com.password926.agijagi.post.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDetailReader {
    private final PostReader postReader;
    private final PostMediaReader postMediaReader;

    @Transactional(readOnly = true)
    public PostDetail readPostDetail(long postId) {
        Post post = postReader.read(postId);
        List<String> mediaUrls = postMediaReader.readPostMediaUrls(postId);

        return PostDetail.builder()
                .postId(postId)
                .title(post.getTitle())
                .content(post.getContent())
                .writerId(post.getWriter().getId())
                .writerNickname(post.getWriter().getNickname())
                .createdAt(post.getCreatedAt())
                .mediaUrls(mediaUrls)
                .build();
    }
}
