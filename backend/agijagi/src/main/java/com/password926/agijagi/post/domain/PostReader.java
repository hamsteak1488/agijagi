package com.password926.agijagi.post.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Post read(long postId) {
        Post post = postRepository.findByIdAndIsDeletedIsFalse(postId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        return post;
    }
}
