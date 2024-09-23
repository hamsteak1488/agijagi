package com.password926.agijagi.story.service;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.story.repository.StoryGPT;
import com.password926.agijagi.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final ChildRepository childRepository;
    private final DiaryRepository diaryRepository;
    private final StoryGPT storyGPT;

    public CreateStoryRequest createStory(long memberId, CreateStoryRequest request) {
        // member가 권한이 있는지검증

        Child child = childRepository.findById(request.getChildId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 아이가 없습니다."));

        LocalDate startTime = request.getStartTime();
        LocalDate endTime = request.getEndTime();

        List<Diary> diaries = diaryRepository.findAllByChildIdAndDateBetween(
                request.getChildId(), startTime, endTime
        );

        String childName = child.getName();
        LocalDate childBirthday = child.getBirthday();
        LocalDate today = LocalDate.now();
        Long age = ChronoUnit.DAYS.between(childBirthday, today);

        return storyGPT.getCreateStoryDtoFromQuery(diaries, childName, age);
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
