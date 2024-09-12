package com.password926.agijagi.diary.repository;

import com.password926.agijagi.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findAllByChildId(long childId);

    Diary findById(long diaryId);
}