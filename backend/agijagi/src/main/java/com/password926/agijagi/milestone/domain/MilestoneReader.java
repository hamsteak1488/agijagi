package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.milestone.infrastructure.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MilestoneReader {

    private final MilestoneRepository milestoneRepository;

    public List<Milestone> readAll() {
        return milestoneRepository.findAll();
    }
}
