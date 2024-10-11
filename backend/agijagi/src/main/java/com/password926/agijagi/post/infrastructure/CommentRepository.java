package com.password926.agijagi.post.infrastructure;

import com.password926.agijagi.post.domain.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends Repository<Comment, Long> {
    Comment save(Comment comment);
    Optional<Comment> findByIdAndIsDeletedIsFalse(long id);

    @Query("SELECT c FROM Comment c join fetch c.writer WHERE c.post.id = :postId and c.isDeleted = false")
    List<Comment> findByPostIdMemberFetch(long postId);
}
