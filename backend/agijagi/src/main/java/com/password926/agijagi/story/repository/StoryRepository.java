package com.password926.agijagi.story.repository;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByChildIdAndIsDeletedFalse(long childId);

    Optional<Story> findByIdAndIsDeletedFalse(long storyId);

}
