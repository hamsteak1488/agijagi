package com.password926.agijagi.post.infrastructure;

import com.password926.agijagi.post.domain.Post;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PostRepository extends Repository<Post, Long> {
    Post save(Post post);
    Optional<Post> findByIdAndIsDeletedIsFalse(Long id);
}
