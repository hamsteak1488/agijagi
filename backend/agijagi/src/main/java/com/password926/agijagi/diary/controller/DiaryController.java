package com.password926.agijagi.diary.controller;

import com.password926.agijagi.diary.controller.dto.CreateDiaryRequest;
import com.password926.agijagi.diary.controller.dto.LoginMember;
import com.password926.agijagi.diary.controller.dto.ReadDiaryRequest;
import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.service.*;
import com.password926.agijagi.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@RequestMapping("/diary")
@RestController
public class DiaryController {

    private DiaryService diaryService;

    @PostMapping
    public ResponseEntity<Void> createDiary(
            LoginMember member,
            @RequestBody CreateDiaryRequest createDiaryRequest
    ) {
        diaryService.createDiary(member.getId(),createDiaryRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{childId}")
    public ResponseEntity<List<Diary>> getDiary(
            LoginMember member,
            @RequestParam long childId
    ) {
        return ResponseEntity.ok().body(diaryService.getDiary(member.getId(), childId));
    }
}
