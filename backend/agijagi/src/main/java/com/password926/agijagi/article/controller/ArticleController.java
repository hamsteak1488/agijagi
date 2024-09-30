package com.password926.agijagi.article.controller;

import com.password926.agijagi.article.controller.dto.CreateArticleRequest;
import com.password926.agijagi.article.service.ArticleService;
import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> createArticle(
            LoginMember member,
            CreateArticleRequest createArticleRequest
    ) {
        articleService.createArticle(member.getId(), createArticleRequest);
        return ResponseEntity.ok().build();
    }
}
