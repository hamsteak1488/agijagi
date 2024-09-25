package com.password926.agijagi.diary.entity;

import com.password926.agijagi.media.domain.Media;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long childId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private boolean isDeleted;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryMedia> diaryMediaList = new ArrayList<>();

    public void remove() {
        isDeleted = true;
    };

    public void updateDiary(String title, String content) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }

    public void addMedia(Media media) {
        DiaryMedia diaryMedia = DiaryMedia.builder()
                .diary(this)
                .media(media)
                .build();
        this.diaryMediaList.add(diaryMedia);

    }
}
