package com.password926.agijagi.post.infrastructure;

import com.password926.agijagi.post.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PostRepository extends Repository<Post, Long> {
    Post save(Post post);

    @Query("SELECT p FROM Post p left join fetch p.postMediaList WHERE p.id = :id and p.isDeleted = false")
    Optional<Post> findById(Long id);
}
