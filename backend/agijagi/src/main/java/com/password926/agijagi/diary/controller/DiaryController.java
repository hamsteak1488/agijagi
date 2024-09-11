package com.password926.agijagi.diary.controller;

import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.LoginMember;
import com.password926.agijagi.diary.controller.dto.ReadDiaryRequest;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Setter
@Getter
@RequiredArgsConstructor
@RequestMapping("/diary")
@RestController
public class DiaryController {

    private DiaryService diaryService;

    @PostMapping
    public ResponseEntity<Void> createDiary(
            LoginMember loginMember,
            @RequestBody CreateDiaryRequest createDiaryRequest
    ) {
        diaryService.createDiary(loginMember.getId(),createDiaryRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{childId}")
    public ResponseEntity<Void> getDiary(
            LoginMember loginMember,
            @RequestBody ReadDiaryRequest readDiaryRequest
    ) {
        Diary diary = diaryService.getDiary(loginMember.getId(), readDiaryRequest);
        if (diary == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}

//    @GetMapping("/child/{childId}")
//    public ResponseEntity<List<Diary>> getDiariesByChildId(@PathVariable Long childId) {
//        List<Diary> diaries = diaryService.getDiariesByChildId(childId);
//        return ResponseEntity.ok(diaries);
//    }
