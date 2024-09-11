package com.password926.agijagi.diary.service;

import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.ReadDiaryRequest;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;

import java.time.LocalDateTime;

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

    public Diary getDiary(long childId, ReadDiaryRequest request) {
        return diaryRepository.findAllByChildId(childId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    private Diary updateDiary(Long id, Diary diary) {
        return null;
    }

    public Diary deleteDiary(Long id, Diary diary) {
        return null;
    }
}

