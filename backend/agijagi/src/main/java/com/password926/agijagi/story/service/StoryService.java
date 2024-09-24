package com.password926.agijagi.story.service;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.story.repository.StoryGPT;
import com.password926.agijagi.story.repository.StoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final ChildValidator childValidator;

    public void createStory1(long memberId, CreateStoryRequest request) {
        childValidator.validateWriterRole(memberId, request.getChildId());

        Child child = childRepository.findById(request.getChildId())
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        List<Diary> diaries = diaryRepository.findAllByChildIdAndCreatedAtBetween(
                request.getChildId(),
                request.getStartDate().atStartOfDay(),
                request.getEndDate().atTime(LocalTime.MAX).withNano(0)
        );

        storyGPT.getCreateStoryDtoFromQuery(
                diaries,
                child.getName(),
                ChronoUnit.DAYS.between(child.getBirthday(), LocalDate.now())
        );
    }

    public List<Story> getAllStory(long memberId, long childId) {
        childValidator.validateWriterRole(memberId, childId);

        List<Story> stories = storyRepository.findAllByChildIdAndIsDeletedFalse(childId);

        stories.sort(new Comparator<Story>() {
            @Override
            public int compare(Story o1, Story o2) { return Long.compare(o2.getId(), o1.getId()); }
        });

        return stories;
    }

    public Story getStory(long memberId, long storyId) {
        Story story = storyRepository.findByIdAndIsDeletedFalse(storyId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriterRole(memberId, story.getChildId());

        return story;
    }

    @Transactional
    public void deleteStory(long memberId, long storyId) {
        Story story = storyRepository.findByIdAndIsDeletedFalse(storyId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriterRole(memberId, story.getChildId());

        story.remove();
    }
}
