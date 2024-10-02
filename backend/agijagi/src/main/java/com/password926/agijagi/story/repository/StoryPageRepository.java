package com.password926.agijagi.story.repository;

import com.password926.agijagi.story.entity.StoryPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryPageRepository extends JpaRepository<StoryPage, Long> {

    List<StoryPage> findAllByStoryId(long storyId);
}
