package com.password926.agijagi.story.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.password926.agijagi.story.entity.Story;

import java.util.*;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByChildIdAndIsDeletedFalseOrderByIdDesc(long childId);

    Optional<Story> findByIdAndIsDeletedFalse(long storyId);
}
