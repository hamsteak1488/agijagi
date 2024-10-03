package com.password926.agijagi.post.domain;

import com.password926.agijagi.post.infrastructure.PostRepository;
import com.password926.agijagi.media.domain.Media;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.member.domain.Member;
import com.password926.agijagi.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostAppender {
    private final PostRepository postRepository;

    private final MediaStorage mediaStorage;
    private final MemberReader memberReader;

    @Transactional
    public long append(long writerId, String title, String content, List<MediaResource> mediaResources) {
        Member writer = memberReader.read(writerId);
        List<Media> mediaList = mediaResources.stream().map(mediaStorage::storeAny).toList();
        Post newPost = Post.createPost(writer, title, content, mediaList);

        postRepository.save(newPost);

        return newPost.getId();
    }
}
