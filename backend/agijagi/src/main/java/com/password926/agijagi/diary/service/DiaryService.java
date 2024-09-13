package com.password926.agijagi.diary.service;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.UpdateDiaryRequest;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

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

    public List<Diary> getAllDiary(long memberId, long childId) {
        // 검증

        List<Diary> diaries = diaryRepository.findAllByChildId(childId);

        diaries.sort(new Comparator<Diary>() {
            @Override
            public int compare(Diary o1, Diary o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });

        return diaries;
    }

    public Diary getDiary(long memberId, long diaryId) {
        // 검증 - 회원이 아이에 대한 읽기 or 권한이 있는지
        // diary -> childId 가져와서 검증해야함

        return diaryRepository.findById(diaryId);
    }

    public void updateDiary(Long diaryId, UpdateDiaryRequest request) {
        // 수정할 권한 있는지 확인

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        diary.updateDiary(request.getTitle(), request.getContent());
    }

    public void deleteDiary(Long diaryId) {
        // 삭제 권한 있는지 확인

        diaryRepository.deleteById(diaryId);
    }
}
