package com.password926.agijagi.record.infrastructure;

import static com.password926.agijagi.record.domain.QRecord.record;

import com.password926.agijagi.record.domain.Record;
import com.password926.agijagi.record.domain.RecordType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class RecordCustomRepositoryImpl implements RecordCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Record> findRecords(
            long childId,
            RecordType type,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        return jpaQueryFactory.selectFrom(record)
                .where(record.child.id.eq(childId)
                        .and(record.startDateTime.between(startDateTime, endDateTime))
                        .and(typeEq(type)))
                .fetch();
    }

    @Override
    public Record findLatestRecord(long childId, RecordType type) {
        return jpaQueryFactory.selectFrom(record)
                .where(record.child.id.eq(childId)
                        .and(record.type.eq(type)))
                .orderBy(record.id.desc())
                .fetchOne();
    }

    private BooleanExpression typeEq(RecordType type) {
        return type == null ? null : record.type.eq(type);
    }
}
