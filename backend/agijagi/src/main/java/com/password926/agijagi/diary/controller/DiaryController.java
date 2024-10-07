package com.password926.agijagi.diary.controller;

import com.password926.agijagi.auth.controller.Authenticate;
import com.password926.agijagi.auth.controller.dto.LoginMember;
import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.UpdateDiaryRequest;
import com.password926.agijagi.diary.entity.DiaryDetail;
import com.password926.agijagi.diary.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/diaries")
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    @Authenticate
    @PostMapping
    public ResponseEntity<Void> createDiary(
            LoginMember member,
            CreateDiaryRequest createDiaryRequest
    ) {
        diaryService.createDiary(member.getId(), createDiaryRequest);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @PatchMapping("/{diaryId}")
    public ResponseEntity<Void> updateDiary(
            LoginMember member,
            @PathVariable long diaryId,
            UpdateDiaryRequest updateDiaryRequest
    ) {
        diaryService.updateDiary(member.getId(), diaryId, updateDiaryRequest);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            LoginMember member,
            @PathVariable long diaryId
    ) {
        diaryService.deleteDiary(member.getId(), diaryId);
        return ResponseEntity.ok().build();
    }

    @Authenticate
    @GetMapping
    public ResponseEntity<List<DiaryDetail>> getAllDiary(
            LoginMember member,
            @RequestParam("childId") long childId
    ) {
        return ResponseEntity.ok().body(diaryService.getAllDiary(member.getId(), childId));
    }

    @Authenticate
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetail> getDiary(
            LoginMember member,
            @PathVariable long diaryId
    ) {
        return ResponseEntity.ok().body(diaryService.getDiary(member.getId(), diaryId));
    }
}
