package com.password926.agijagi.article.service;

import com.password926.agijagi.article.controller.dto.CreateArticleRequest;
import com.password926.agijagi.article.entity.Article;
import com.password926.agijagi.article.repository.ArticleRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.Media;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final MediaStorage mediaStorage;

    @Transactional
    public void createArticle(long memberId, CreateArticleRequest request) {
        Member member = memberRepository.findByIdAndIsDeletedIsFalse(memberId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Article article = Article.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        for (MultipartFile multipartFile : request.getMediaList()) {
            Media image = mediaStorage.storeAny(MediaResource.from(multipartFile));
            article.addMedia(image);
        }

        articleRepository.save(article);
    }
}
