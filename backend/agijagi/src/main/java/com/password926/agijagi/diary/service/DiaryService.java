package com.password926.agijagi.diary.service;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.ReadDiaryRequest;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository, DiaryService diaryService) {
        this.diaryRepository = diaryRepository;
    }

    public void createDiary(long memberId, CreateDiaryRequest request) {
        // 검증
        diaryRepository.save(Diary.builder()
                .childId(request.getChildId())
                .memberId(memberId)
                .title(request.getTitle())
                .content(request.getContent())
                .date(LocalDateTime.now())
                .build());
    }

    public List<Diary> getDiary(long memberId, long childId) {
        // 검증

        List<Diary> diaries = diaryRepository.findAllByChildId(childId);

        Collections.sort(diaries, new Comparator<Diary>() {
            @Override
            public int compare(Diary o1, Diary o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });

        return diaries;
    }

    private Diary updateDiary(Long id, Diary diary) {
        return null;
    }

    public Diary deleteDiary(Long id, Diary diary) {
        return null;
    }
}

