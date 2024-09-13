package com.password926.agijagi.story.repository;

import com.password926.agijagi.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByChildId(long childId);

    Story findById(long storyId);

}
