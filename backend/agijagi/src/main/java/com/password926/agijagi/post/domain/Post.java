package com.password926.agijagi.post.domain;

import com.password926.agijagi.media.domain.Media;
import com.password926.agijagi.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private int nextMediaOrderSeq;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostMedia> postMediaList = new ArrayList<>();

    public static Post createPost(Member writer, String title, String content, List<Media> mediaList) {
        Post newPost = new Post();
        newPost.writer = writer;
        newPost.title = title;
        newPost.content = content;

        newPost.createdAt = LocalDateTime.now();
        newPost.isDeleted = false;

        newPost.nextMediaOrderSeq = 1;
        mediaList.forEach(newPost::addMedia);

        return newPost;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void addMedia(Media media) {
        PostMedia postMedia = new PostMedia(this, media, nextMediaOrderSeq++);
        postMediaList.add(postMedia);
    }

    public void removeMedia(UUID mediaId) {
        postMediaList.removeIf(postMedia -> postMedia.getMedia().getId().equals(mediaId));
    }
}
