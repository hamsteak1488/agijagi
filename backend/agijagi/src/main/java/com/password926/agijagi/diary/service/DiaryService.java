package com.password926.agijagi.diary.service;

import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;

public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    private Diary createDiary(Diary diary) {
        Long id = diary.getId();
        diary =  Diary.builder()
                .childId(diary.getChildId())
                .memberId(diary.getMemberId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .build();
        return diaryRepository.save(diary);
    }

    public Diary readDiaryById(Long id, Diary diary) {
        return null;
    }

    private Diary updateDiary(Long id, Diary diary) {
        return null;
    }

    public void deleteDiary(Long id, Diary diary) {
        diaryRepository.deleteById(id);
    }

//    public Diary updateDiary(Long diaryId, DiaryDto diaryDto) {
//        Diary diary = diaryRepository.findById(diaryId)
//                .orElseThrow(() -> new IllegalArgumentException("Diary not found with id: " + diaryId));
//        diary.setTitle(diaryDto.getTitle());
//        diary.setContent(diaryDto.getContent());
//        return diaryRepository.save(diary);
//    }
}

//package com.password926.agijagi.diary.service;
//
//import com.password926.agijagi.diary.dto.DiaryDto;
//import com.password926.agijagi.diary.entity.Diary;
//import com.password926.agijagi.diary.repository.DiaryRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class DiaryService {
//
//    private final DiaryRepository diaryRepository;
//
//    public DiaryService(DiaryRepository diaryRepository) {
//        this.diaryRepository = diaryRepository;
//    }
//
//    // 일기 생성
//    public Diary createDiary(DiaryDto diaryDto) {
//        Diary diary = Diary.builder()
//                .childId(diaryDto.getChildId())
//                .memberId(diaryDto.getMemberId())
//                .title(diaryDto.getTitle())
//                .content(diaryDto.getContent())
//                .date(diaryDto.getDate())
//                .build();
//        return diaryRepository.save(diary);
//    }
//
//    // 특정 childId로 일기 목록 조회
//    public List<Diary> getDiariesByChildId(Long childId) {
//        return diaryRepository.findAllByChildId(childId);
//    }
//
//    // 특정 id로 일기 조회
//    public Diary getDiaryById(Long id) {
//        return diaryRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Diary not found with id: " + id));
//    }
//
//    // 일기 수정
//    public Diary updateDiary(Long id, DiaryDto diaryDto) {
//        Diary diary = diaryRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Diary not found with id: " + id));
//        diary.setTitle(diaryDto.getTitle());
//        diary.setContent(diaryDto.getContent());
//        return diaryRepository.save(diary);
//    }
//
//    // 일기 삭제
//    public void deleteDiary(Long id) {
//        diaryRepository.deleteById(id);
//    }
//}
