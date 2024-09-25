package com.password926.agijagi.record.infrastructure;

import com.password926.agijagi.record.domain.Record;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface RecordRepository extends Repository<Record, Long> {

    Optional<Record> findById(long id);

    void save(Record record);

    void delete(Record record);
}
