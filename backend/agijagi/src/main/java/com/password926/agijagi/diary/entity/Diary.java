package com.password926.agijagi.diary.entity;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.media.domain.Media;
import com.password926.agijagi.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean isDeleted;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryMedia> diaryMediaList = new ArrayList<>();

    @Builder
    public Diary(Child child, Member member, LocalDateTime createdAt, String title, String content) {
        this.child = child;
        this.member = member;
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
    }

    public void remove() {
        isDeleted = true;
    }

    public void updateTitleOrContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addMedia(Media media) {
        DiaryMedia diaryMedia = DiaryMedia.builder()
                .diary(this)
                .media(media)
                .build();
        this.diaryMediaList.add(diaryMedia);
    }

    public void removeMedia(DiaryMedia diaryMedia) {
        this.diaryMediaList.remove(diaryMedia);
    }
}
