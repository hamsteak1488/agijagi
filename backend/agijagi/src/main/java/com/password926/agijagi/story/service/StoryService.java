package com.password926.agijagi.story.service;

import com.password926.agijagi.ai.domain.ImageGenerator;
import com.password926.agijagi.ai.domain.JsonFormatter;
import com.password926.agijagi.story.controller.dto.CreateStoryRequest;
import com.password926.agijagi.story.repository.StoryPageRepository;
import com.password926.agijagi.story.repository.StoryRepository;
import com.password926.agijagi.story.entity.StoryPageDetail;
import com.password926.agijagi.story.entity.StoryDetail;
import com.password926.agijagi.story.repository.StoryGPT;
import com.password926.agijagi.story.entity.StoryPage;
import com.password926.agijagi.story.entity.Story;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.diary.entity.Diary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.time.*;
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
    private final ImageGenerator imageGenerator;

    @Transactional
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
        story.addMedia(image);

        storyRepository.save(story);

        List<String> contents = new ArrayList<>();

        List<StoryPage> storyPageData = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<Map<String, Object>> storyPages = objectMapper.readValue(JsonFormatter.format(storyData), new TypeReference<List<Map<String, Object>>>(){});
            for (Map<String, Object> page : storyPages) {
                StoryPage pageData = StoryPage.builder()
                        .story(story)
                        .content((String) page.get("content"))
                        .pageNumber((Integer) page.get("pageNumber"))
                        .build();
                storyPageData.add(pageData);
                contents.add((String) page.get("content"));
            }
        } catch (Exception e) {
            throw new RestApiException(CommonErrorCode.INTERNAL_SERVER_ERROR, e);
        }

        List<Image> images = imageGenerator.generate(contents);

        for (int i = 0; i < images.size(); i++) {
            storyPageData.get(i).addMedia(images.get(i));
        }
        storyPageRepository.saveAll(storyPageData);
    }

    @Transactional(readOnly = true)
    public List<StoryDetail> getAllStory(long memberId, long childId) {

        childValidator.validateWriteAuthority(memberId, childId);

        List<Story> stories = storyRepository.findAllByChildIdAndIsDeletedFalseOrderByIdDesc(childId);

        List<StoryDetail> storyDetails = new ArrayList<>();

        for (Story story : stories) {
            StoryDetail storyDetail = StoryDetail.of(story);
            storyDetails.add(storyDetail);
        }

        return storyDetails;
    }

    @Transactional(readOnly = true)
    public StoryDetail getStory(long memberId, long storyId) {

        Story story = storyRepository.findByIdAndIsDeletedFalse(storyId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, story.getChild().getId());

        return StoryDetail.of(story);
    }

    @Transactional(readOnly = true)
    public List<StoryPageDetail> getStoryAllPage(long memberId, long storyId) {

        Story story = storyRepository.findByIdAndIsDeletedFalse(storyId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, story.getChild().getId());

        List<StoryPage> storyPages = storyPageRepository.findAllByStoryId(storyId);

        List<StoryPageDetail> storyPageDetails = new ArrayList<>();

        for (StoryPage storyPage : storyPages) {
            StoryPageDetail storyPageDetail = StoryPageDetail.of(storyPage);
            storyPageDetails.add(storyPageDetail);
        }

        return storyPageDetails;
    }

    @Transactional
    public void deleteStory(long memberId, long storyId) {

        Story story = storyRepository.findByIdAndIsDeletedFalse(storyId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, story.getChild().getId());

        storyPageRepository.deleteAllByStoryId(storyId);

        story.remove();
    }
}
