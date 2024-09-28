package com.password926.agijagi.growth.infrastructure;

import com.password926.agijagi.growth.domain.Growth;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface GrowthRepository extends Repository<Growth, Long> {

    Optional<Growth> findByChildIdAndMonth(long childId, int month);

    List<Growth> findAllByChildId(long childId);

    void save(Growth growth);
}
