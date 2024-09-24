package com.password926.agijagi.record.infrastructure;

import com.password926.agijagi.record.domain.Record;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends Repository<Record, Long> {

    Optional<Record> findById(long id);

    List<Record> findAllByChildIdAndDateTimeBetween(long childId, LocalDateTime start, LocalDateTime end);

    void save(Record record);

    void delete(Record record);
}
