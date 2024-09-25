package com.password926.agijagi.diary.repository;

import com.password926.agijagi.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<Diary> findAllByChildIdAndIsDeletedFalse(long childId);

    Optional<Diary> findByIdAndIsDeletedFalse(long diaryId);

    List<Diary> findAllByChildIdAndCreatedAtBetween(Long childId, LocalDateTime createAt, LocalDateTime createAt2);

}