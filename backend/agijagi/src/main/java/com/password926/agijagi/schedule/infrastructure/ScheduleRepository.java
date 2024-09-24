package com.password926.agijagi.schedule.infrastructure;

import com.password926.agijagi.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByChildIdAndStartDateTimeBetween(long childId, LocalDateTime start, LocalDateTime end);
}
