package com.password926.agijagi.post.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.post.controller.dto.request.UpdatePostRequest;
import com.password926.agijagi.post.domain.PostDetail;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.post.controller.dto.request.CreatePostRequest;
import com.password926.agijagi.post.domain.PostPageRequest;
import com.password926.agijagi.post.domain.PostSearchFilter;
import com.password926.agijagi.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> createPost(LoginMember loginMember,
                                           CreatePostRequest request)
    {
        List<MediaResource> mediaResources = request.getMediaList() != null
                ? request.getMediaList().stream().map(MediaResource::from).toList() : List.of();

        long postId = postService.createPost(
                loginMember.getId(),
                request.getTitle(),
                request.getContent(),
                mediaResources
        );

        return ResponseEntity.created(
                UriComponentsBuilder
                        .fromPath("/posts/{postId}")
                        .buildAndExpand(postId)
                        .toUri()
                )
                .build();
    }

    @Authenticate
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(LoginMember loginMember,
                                           @PathVariable long postId,
                                           @Valid UpdatePostRequest request)
    {
        List<MediaResource> newMediaList = request.getNewMediaList() != null
                ? request.getNewMediaList().stream().map(MediaResource::from).toList() : List.of();
        List<UUID> removeMediaIdList = request.getDeleteMediaIdList() != null
                ? request.getDeleteMediaIdList() : List.of();


        postService.updatePost(
                loginMember.getId(),
                postId,
                request.getTitle(),
                request.getContent(),
                newMediaList,
                removeMediaIdList
        );

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(LoginMember loginMember,
                                           @PathVariable long postId)
    {
        postService.deletePost(loginMember.getId(), postId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetail> getPost(@PathVariable long postId) {
        PostDetail postDetail = postService.getPostDetail(postId);

        return ResponseEntity.ok(postDetail);
    }

    @GetMapping
    public ResponseEntity<Page<PostDetail>> getPostPage(PostPageRequest request,
                                                        @PageableDefault Pageable pageable) {
        Page<PostDetail> postDetailPage = postService.getPostDetailPage(
                new PostSearchFilter(request.getTitle(), request.getWriterNickname()),
                pageable
        );

        return ResponseEntity.ok(postDetailPage);
    }
}
