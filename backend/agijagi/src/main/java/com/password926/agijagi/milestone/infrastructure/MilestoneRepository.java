package com.password926.agijagi.milestone.infrastructure;

import com.password926.agijagi.milestone.domain.Milestone;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MilestoneRepository extends Repository<Milestone, Long> {

    List<Milestone> findAll();

    void save(Milestone milestone);
}
