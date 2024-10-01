package com.password926.agijagi.story.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoryPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int pageNumber;

//    private String image;

    @Builder
    public StoryPage(Story story, String content, int pageNumber) {
        this.story = story;
        this.content = content;
        this.pageNumber = pageNumber;
    }
}
