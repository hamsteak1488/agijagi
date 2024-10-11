package com.password926.agijagi.post.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.post.controller.dto.request.CreateCommentRequest;
import com.password926.agijagi.post.controller.dto.request.UpdateCommentRequest;
import com.password926.agijagi.post.domain.CommentDetail;
import com.password926.agijagi.post.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Authenticate
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> createComment(LoginMember loginMember,
                                              @PathVariable long postId,
                                              @RequestBody CreateCommentRequest request) {
        long commentId = commentService.createComment(
                loginMember.getId(),
                postId,
                request.getParentCommentId(),
                request.getContent()
        );

        return ResponseEntity.created(
                UriComponentsBuilder.fromPath("/posts/{postId}/comments/{commentId}")
                        .buildAndExpand(postId, commentId)
                        .toUri()
                )
                .build();
    }

    @Authenticate
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> updatePostComment(LoginMember loginMember,
                                                  @PathVariable long commentId,
                                                  @RequestBody UpdateCommentRequest request) {
        commentService.updateComment(loginMember.getId(), commentId, request.getContent());

        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deletePostComment(LoginMember loginMember,
                                                  @PathVariable long commentId) {
        commentService.deleteComment(loginMember.getId(), commentId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDetail>> getPostComments(@PathVariable long postId) {
        List<CommentDetail> commentDetails = commentService.getPostCommentDetails(postId);

        return ResponseEntity.ok(commentDetails);
    }
}
