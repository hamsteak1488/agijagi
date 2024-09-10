package com.password926.agijagi.diary.controller;

import com.password926.agijagi.diary.entity.Diary;
import com.password926.agijagi.diary.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Setter
@Getter
@RequiredArgsConstructor
@RequestMapping("/diary")
@RestController
public class DiaryController {

    private DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

//    public ResponseEntity<?> createDiary(Diary diary) {
//    }
}

//package com.password926.agijagi.diary.controller;
//
//import com.password926.agijagi.diary.dto.DiaryDto;
//import com.password926.agijagi.diary.entity.Diary;
//import com.password926.agijagi.diary.service.DiaryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//        import java.util.List;
//
//@RequiredArgsConstructor
//@RequestMapping("/diary")
//@RestController
//public class DiaryController {
//
//    private final DiaryService diaryService;
//
//    // 일기 생성
//    @PostMapping
//    public ResponseEntity<Diary> createDiary(@RequestBody DiaryDto diaryDto) {
//        Diary createdDiary = diaryService.createDiary(diaryDto);
//        return ResponseEntity.ok(createdDiary);
//    }
//
//    // 특정 childId로 일기 목록 조회
//    @GetMapping("/child/{childId}")
//    public ResponseEntity<List<Diary>> getDiariesByChildId(@PathVariable Long childId) {
//        List<Diary> diaries = diaryService.getDiariesByChildId(childId);
//        return ResponseEntity.ok(diaries);
//    }
//
//    // 특정 id로 일기 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<Diary> getDiaryById(@PathVariable Long id) {
//        Diary diary = diaryService.getDiaryById(id);
//        return ResponseEntity.ok(diary);
//    }
//
//    // 일기 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<Diary> updateDiary(@PathVariable Long id, @RequestBody DiaryDto diaryDto) {
//        Diary updatedDiary = diaryService.updateDiary(id, diaryDto);
//        return ResponseEntity.ok(updatedDiary);
//    }
//
//    // 일기 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDiary(@PathVariable Long id) {
//        diaryService.deleteDiary(id);
//        return ResponseEntity.noContent().build();
//    }
//}
