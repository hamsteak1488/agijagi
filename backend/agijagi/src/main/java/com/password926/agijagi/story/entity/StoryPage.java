package com.password926.agijagi.story.entity;

import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.Media;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private Media storyImage;

    @Builder
    public StoryPage(Story story, String content, int pageNumber) {
        this.story = story;
        this.content = content;
        this.pageNumber = pageNumber;
    }

    public void addMedia(Media media) {
        this.storyImage = media;
    }
}
