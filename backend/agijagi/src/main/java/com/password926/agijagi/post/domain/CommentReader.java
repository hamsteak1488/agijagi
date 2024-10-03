package com.password926.agijagi.post.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.post.infrastructure.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentReader {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Comment read(long commentId) {
        return commentRepository.findByIdAndIsDeletedIsFalse(commentId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }
}
