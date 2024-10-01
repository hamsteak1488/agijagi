package com.password926.agijagi.story.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.story.entity.StoryPage;
import com.password926.agijagi.story.repository.StoryGPT;
import com.password926.agijagi.story.repository.StoryPageRepository;
import com.password926.agijagi.story.repository.StoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryPageRepository storyPageRepository;
    private final ChildRepository childRepository;
    private final DiaryRepository diaryRepository;
    private final StoryGPT storyGPT;
    private final ChildValidator childValidator;
    private final MediaStorage mediaStorage;
    private final ObjectMapper objectMapper;

    public void createStory(long memberId, CreateStoryRequest request) {
        childValidator.validateWriteAuthority(memberId, request.getChildId());

        Child child = childRepository.findByIdAndIsDeletedFalse(request.getChildId())
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        List<Diary> diaries = diaryRepository.findAllByChildIdAndCreatedAtBetween(
                request.getChildId(),
                request.getStartDate().atStartOfDay(),
                request.getEndDate().atTime(LocalTime.MAX).withNano(0)
        );

        Story story = Story.builder()
                .child(child)
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createdAt(LocalDateTime.now())
                .build();

        String storyData = storyGPT.getCreateStoryDtoFromQuery(
                diaries,
                child.getName(),
                ChronoUnit.DAYS.between(child.getBirthday(), LocalDate.now())
        );

        Image image = mediaStorage.storeImage(MediaResource.from(request.getCoverImage()));
        story.addMedia(image.getUrl());

        storyRepository.save(story);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<Map<String, Object>> storyPages = objectMapper.readValue(storyData, new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> page : storyPages) {
                StoryPage storyPageData = StoryPage.builder()
                        .story(story)
                        .content((String) page.get("content"))
                        .pageNumber((Integer) page.get("pageNumber"))
                        .build();
                storyPageRepository.save(storyPageData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
