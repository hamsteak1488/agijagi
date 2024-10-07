package com.password926.agijagi.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.password926.agijagi.story.entity.StoryPage;

import java.util.List;

public interface StoryPageRepository extends JpaRepository<StoryPage, Long> {

    List<StoryPage> findAllByStoryId(long storyId);

    void deleteAllByStoryId(long storyId);
}
