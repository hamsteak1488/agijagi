package com.password926.agijagi.diary.repository;

import com.password926.agijagi.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.*;
import java.util.*;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findAllByChildIdAndIsDeletedFalseOrderByIdDesc(long childId);

    Optional<Diary> findByIdAndIsDeletedFalse(long diaryId);

    List<Diary> findAllByChildIdAndWroteAtBetween(Long childId, LocalDate wroteAt1, LocalDate createAt2);
}
