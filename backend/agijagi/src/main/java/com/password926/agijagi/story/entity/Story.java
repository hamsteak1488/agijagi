package com.password926.agijagi.story.entity;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.media.domain.Media;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Short coverImageIndex;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private boolean isDeleted;

    @Builder
    public Story(Child child, String title, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt) {
        this.child = child;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

    public void addCoverImage(Short coverImageIndex) {
        this.coverImageIndex = coverImageIndex;
    }

    public void remove() {
        isDeleted = true;
    }
}
