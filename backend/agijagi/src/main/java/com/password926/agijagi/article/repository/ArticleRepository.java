package com.password926.agijagi.article.repository;

import com.password926.agijagi.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByMemberIdAndIsDeletedFalse(long memberId);

    Optional<Article> findByMemberIdAndIsDeletedFalse(long articleId);

}
