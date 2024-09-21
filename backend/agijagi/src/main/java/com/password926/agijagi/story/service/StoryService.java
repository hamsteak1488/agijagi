package com.password926.agijagi.story.service;

import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public List<Story> getAllStory(long memberId, long childId) {
        //검증

        List<Story> stories = storyRepository.findAllByChildId(childId);

        stories.sort(new Comparator<Story>() {
            @Override
            public int compare(Story o1, Story o2) { return Long.compare(o2.getId(), o1.getId()); }
        });

        return stories;
    }

    public Story getStory(long memberId, long storyId) {
        //검증

        return storyRepository.findById(storyId);
    }

    public void deleteStory(long storyId) {
        //검증

        storyRepository.deleteById(storyId);
    }
}
