package com.password926.agijagi.story.service;

import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public String createStory(long memberId, CreateStoryRequest request) {
        // member가 권한이 있는지검증

        // 아이 정보를 DB에서 가져오기
        // 요청에서 받은 기간 동안의 일기를 DB에서 꺼내서 가져오기

        Story story = storyRepository.save(Story.builder()
                .childId(request.getChildId())
                .startTime(LocalDateTime.parse(request.getStartTime()))
                .endTime(LocalDateTime.parse(request.getEndTime()))
                .createAt(LocalDateTime.now())
                .build());

        return story.getText();
    }

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
