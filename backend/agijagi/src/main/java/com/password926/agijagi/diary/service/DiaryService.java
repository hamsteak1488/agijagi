package com.password926.agijagi.diary.service;

import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.DeleteDiaryRequest;
import com.password926.agijagi.diary.controller.dto.UpdateDiaryRequest;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.diary.entity.DiaryDetail;
import com.password926.agijagi.diary.entity.DiaryMedia;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import com.password926.agijagi.member.domain.Member;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final MemberRepository memberRepository;
    private final ChildRepository childRepository;
    private final DiaryRepository diaryRepository;
    private final ChildValidator childValidator;
    private final MediaStorage mediaStorage;

    @Transactional
    public void createDiary(long memberId, CreateDiaryRequest request) {
        childValidator.validateWriteAuthority(memberId, request.getChildId());

        Child child = childRepository.findByIdAndIsDeletedFalse(request.getChildId())
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Diary diary = Diary.builder()
                .child(child)
                .member(member)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        if (request.getMediaList() != null) {
            for (MultipartFile multipartFile : request.getMediaList() ) {
                Image image = mediaStorage.storeImage(MediaResource.from(multipartFile));
                diary.addMedia(image);
            }
        }

        diaryRepository.save(diary);
    }

    @Transactional
    public void updateDiary(long memberId, long diaryId, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, diary.getChild().getId());

        diary.updateContent(request.getContent());

        if (request.getRemoveMediaIdList() != null) {
            for (UUID removeMediaId : request.getRemoveMediaIdList()) {
                List<DiaryMedia> diaryMediaList = List.copyOf(diary.getDiaryMediaList());
                for (DiaryMedia diaryMedia : diaryMediaList) {
                    if (diaryMedia.getMedia().getId().equals(removeMediaId)) {
                        diary.removeMedia(diaryMedia);
                    }
                }
            }
        }

        if (request.getNewMediaList() != null) {
            for (MultipartFile multipartFile : request.getNewMediaList() ) {
                Image image = mediaStorage.storeImage(MediaResource.from(multipartFile));
                diary.addMedia(image);
            }
        }

        diaryRepository.save(diary);
    }

    @Transactional
    public void deleteDiary(long memberId, long diaryId, DeleteDiaryRequest request) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, diary.getChild().getId());

        if (request.getRemoveMediaIdList() != null) {
            for (UUID removeMediaId : request.getRemoveMediaIdList()) {
                List<DiaryMedia> diaryMediaList = List.copyOf(diary.getDiaryMediaList());
                for (DiaryMedia diaryMedia : diaryMediaList) {
                    if (diaryMedia.getMedia().getId().equals(removeMediaId)) {
                        diary.removeMedia(diaryMedia);
                    }
                }
            }
        }

        diary.remove();
    }

    @Transactional(readOnly = true)
    public List<DiaryDetail> getAllDiary(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);

        List<Diary> diaries = diaryRepository.findAllByChildIdAndIsDeletedFalseOrderByIdDesc(childId);

        List<DiaryDetail> diaryDetails = new ArrayList<>();

        for (Diary diary : diaries) {
            DiaryDetail diaryDetail = DiaryDetail.of(diary);
            if (diary.getDiaryMediaList() != null) {
                for (DiaryMedia diaryMedia : diary.getDiaryMediaList()) {
                    diaryDetail.getMediaUrls().add(diaryMedia.getMedia().getUrl());
                }
            }
            diaryDetails.add(diaryDetail);
        }

        return diaryDetails;
    }

    @Transactional(readOnly = true)
    public DiaryDetail getDiary(long memberId, long diaryId) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                        .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, diary.getChild().getId());

        DiaryDetail diaryDetail = DiaryDetail.of(diary);

        if (diary.getDiaryMediaList() != null) {
            for (DiaryMedia diaryMedia : diary.getDiaryMediaList()) {
                diaryDetail.getMediaUrls().add(diaryMedia.getMedia().getUrl());
            }
        }

        return diaryDetail;
    }
}
