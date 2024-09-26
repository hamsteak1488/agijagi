package com.password926.agijagi.article.entity;

import com.password926.agijagi.media.domain.Media;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private boolean isDeleted;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleMedia> articleMediaList = new ArrayList<>();

    @Builder
    public Article(Long memberId, String title, String content, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void remove() {
        isDeleted = true;
    }

    public void updateArticle(String title, String content) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }

    public void addMedia(Media media) {
        ArticleMedia articleMedia = ArticleMedia.builder()
                .article(this)
                .media(media)
                .build();
        this.articleMediaList.add(articleMedia);

    }
}
