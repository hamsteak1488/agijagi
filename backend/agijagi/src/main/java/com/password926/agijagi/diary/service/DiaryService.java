package com.password926.agijagi.diary.service;

import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.UpdateDiaryRequest;
import com.password926.agijagi.diary.repository.DiaryRepository;
import com.password926.agijagi.diary.entity.DiaryDetail;
import com.password926.agijagi.diary.entity.DiaryMedia;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.media.domain.*;
import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import com.password926.agijagi.member.domain.Member;
import org.hibernate.Hibernate;
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

        Member member = memberRepository.findByIdAndIsDeletedIsFalse(memberId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        Diary diary = Diary.builder()
                .child(child)
                .member(member)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .wroteAt(request.getWroteAt())
                .build();

        if (request.getMediaList() != null) {
            for (MultipartFile multipartFile : request.getMediaList() ) {
                Media media = mediaStorage.storeAny(MediaResource.from(multipartFile));
                diary.addMedia(media);
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
                Media media = mediaStorage.storeAny(MediaResource.from(multipartFile));
                diary.addMedia(media);
            }
        }
    }

    @Transactional
    public void deleteDiary(long memberId, long diaryId) {
        Diary diary = diaryRepository.findByIdAndIsDeletedFalse(diaryId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        childValidator.validateWriteAuthority(memberId, diary.getChild().getId());

        List<DiaryMedia> diaryMediaList = List.copyOf(diary.getDiaryMediaList());
        for (DiaryMedia diaryMedia : diaryMediaList) {
            diary.removeMedia(diaryMedia);
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
                    if (Hibernate.getClass(diaryMedia.getMedia()).equals(Video.class)) {
                        diaryDetail.getMediaTypes().add("video");
                    }else if (Hibernate.getClass(diaryMedia.getMedia()).equals(Image.class)) {
                        diaryDetail.getMediaTypes().add("image");
                    }
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
                if (Hibernate.getClass(diaryMedia.getMedia()).equals(Video.class)) {
                    diaryDetail.getMediaTypes().add("video");
                }else if (Hibernate.getClass(diaryMedia.getMedia()).equals(Image.class)) {
                    diaryDetail.getMediaTypes().add("image");
                }
            }
        }

        return diaryDetail;
    }
}
