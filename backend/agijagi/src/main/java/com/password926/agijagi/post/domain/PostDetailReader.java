package com.password926.agijagi.post.domain;

import com.password926.agijagi.post.infrastructure.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PostDetailReader {
    private final PostReader postReader;
    private final PostMediaReader postMediaReader;
    private final PostQueryRepository postQueryRepository;

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

    @Transactional(readOnly = true)
    public Page<PostDetail> readPostDetailPage(PostSearchFilter filter,  Pageable pageable) {
        Page<Post> postPage = postQueryRepository.getPostPage(filter, pageable);

        List<PostDetail> postDetails = postPage.stream()
                .map(p -> PostDetail.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .writerId(p.getWriter().getId())
                        .writerNickname(p.getWriter().getNickname())
                        .createdAt(p.getCreatedAt())
                        .mediaUrls(postMediaReader.readPostMediaUrls(p.getId()))
                        .build())
                .toList();

        return new PageImpl<>(postDetails, postPage.getPageable(), postPage.getTotalElements());
    }
}
