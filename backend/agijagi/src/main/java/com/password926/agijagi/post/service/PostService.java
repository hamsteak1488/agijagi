package com.password926.agijagi.post.service;

import com.password926.agijagi.post.domain.*;
import com.password926.agijagi.media.domain.MediaResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostAppender postAppender;
    private final PostDetailReader postDetailReader;
    private final PostUpdater postUpdater;
    private final PostDeleter postDeleter;
    private final PostAuthorityValidator postAuthorityValidator;

    public long createPost(long requesterId, String title, String content, List<MediaResource> mediaList) {
        return postAppender.append(requesterId, title, content, mediaList);
    }

    public void updatePost(long requesterId, long postId, String title, String content, List<MediaResource> newMediaList, List<UUID> removeMediaIds) {
        postAuthorityValidator.validate(requesterId, postId);

        postUpdater.updatePost(postId, title, content, newMediaList, removeMediaIds);
    }

    public void deletePost(long requesterId, long postId) {
        postAuthorityValidator.validate(requesterId, postId);

        postDeleter.deletePost(postId);
    }

    public PostDetail getPostDetail(long postId) {
        return postDetailReader.readPostDetail(postId);
    }

    public Page<PostDetail> getPostDetailPage(PostSearchFilter filter, Pageable pageable) {
        return postDetailReader.readPostDetailPage(filter, pageable);
    }
}
