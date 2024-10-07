package com.password926.agijagi.post.infrastructure;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.post.domain.Post;
import com.password926.agijagi.post.domain.PostSearchFilter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.password926.agijagi.post.domain.QPost.post;

@Repository
public class PostQueryRepository {
    private final JPAQueryFactory query;

    public PostQueryRepository(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    public Page<Post> getPostPage(PostSearchFilter filter, Pageable pageable) {
        List<Post> posts = query
                .select(post)
                .from(post)
                .where(
                        Expressions.TRUE
                                .and(likeTitle(filter.getTitle()))
                                .and(likeWriterNickname(filter.getWriterNickname()))
                                .and(post.isDeleted.isFalse())
                )
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long resultCount = query
                .select(post.count())
                .from(post)
                .where(
                        Expressions.TRUE
                                .and(likeTitle(filter.getTitle()))
                                .and(likeWriterNickname(filter.getWriterNickname()))
                                .and(post.isDeleted.isFalse())
                )
                .fetchFirst();

        if (resultCount == null) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR, "게시글 조회 과정에서 서버에 문제가 발생했습니다.");
        }

        return PageableExecutionUtils.getPage(posts, pageable, () -> resultCount);
    }

    private BooleanExpression likeTitle(String title) {
        if (title != null) {
            return post.title.like("%" + title + "%");
        }
        else {
            return null;
        }
    }

    private BooleanExpression likeWriterNickname(String writerNickname) {
        if (writerNickname != null) {
            return post.writer.nickname.like("%" + writerNickname + "%");
        }
        else {
            return null;
        }
    }
}
