package com.password926.agijagi.diary.service;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.UpdateDiaryRequest;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final ChildRepository childRepository;
    private final DiaryRepository diaryRepository;
    private final ChildValidator childValidator;
    private final MediaStorage mediaStorage;

    @Transactional
    public void createDiary(long memberId, CreateDiaryRequest request) {
        childValidator.validateWriterRole(memberId, request.getChildId());

        Child child = childRepository.findById(request.getChildId())
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Diary diary = Diary.builder()
                .childId(request.getChildId())
                .memberId(memberId)
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        for (MultipartFile multipartFile : request.getMediaList() ) {
            Image image = mediaStorage.storeImage(multipartFile.getResource(), multipartFile.getContentType());
            diary.addMedia(image);
        }

        diaryRepository.save(diary);

    }

    @Transactional
    public void updateDiary(long memberId, long diaryId, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriterRole(memberId, diary.getChildId());

        diary.updateDiary(request.getTitle(), request.getContent());

        for (MultipartFile multipartFile : request.getMediaList() ) {
            Image image = mediaStorage.storeImage(multipartFile.getResource(), multipartFile.getContentType());
            diary.addMedia(image);
        }

        diaryRepository.save(diary);

    }

    public List<Diary> getAllDiary(long memberId, long childId) {
        childValidator.validateWriterRole(memberId, childId);

        List<Diary> diaries = diaryRepository.findAllByChildIdAndIsDeletedFalse(childId);

        diaries.sort(new Comparator<Diary>() {
            @Override
            public int compare(Diary o1, Diary o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });

        return diaries;
    }

    public Diary getDiary(long memberId, long diaryId) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                        .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriterRole(memberId, diary.getChildId());

        return diary;
    }

    @Transactional
    public void deleteDiary(long memberId, long diaryId) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriterRole(memberId, diary.getChildId());

        diary.remove();
    }
}
