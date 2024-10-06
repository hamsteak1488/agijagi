package com.password926.agijagi.post.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentAuthorityValidator {
    private final CommentReader commentReader;

    @Transactional(readOnly = true)
    public void validate(long memberId, long postId) {
        long writerId = commentReader.read(postId).getWriter().getId();

        if (memberId != writerId) {
            throw new RestApiException(CommonErrorCode.FORBIDDEN, "댓글 수정&삭제 권한이 없습니다.");
        }
    }
}
