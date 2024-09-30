package com.password926.agijagi.report.domain;

import com.password926.agijagi.ai.domain.CreatorUsingOpenAi;
import com.password926.agijagi.ai.domain.JsonObjectMapper;
import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.growth.domain.GrowthContentReader;
import com.password926.agijagi.milestone.domain.MilestoneStateDetail;
import com.password926.agijagi.milestone.domain.MilestoneStateDetailReader;
import com.password926.agijagi.report.infrastructure.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ReportAppender {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final GrowthContentReader growthContentReader;
    private final MilestoneStateDetailReader milestoneStateDetailReader;
    private final CreatorUsingOpenAi creatorUsingOpenAi;
    private final JsonObjectMapper jsonObjectMapper;
    private final ReportRepository reportRepository;
    private final PromptTemplate promptTemplate = new PromptTemplate(
            """
            The challenge is to use growth and milestone data to create a child's growth report. This report should be prepared by comparing the average growth data of other babies with the child's birth weight and growth data.
                        
            First, the following information is provided:
        
            <birthWeight>
            birthWeight : {birthWeight}
            </birthWeight>
           
            <growth>
            growth : {growth}
            </growth>
                        
            <milestone>
            milestone: {milestone}
            </milestone>
                           
            Use this information to create a baby growth report that incorporates baby growth and milestones.
            Follow these instructions:
                        
           1. "maxDegree" must be 10
           2. "growthDegree" must be less than "maxDegree" and is a relative degree of growth compared to other babies
           3. The baby's growth report should include overall growth status, weight changes, and key developmental milestones
           4. The report may include advice for parents or predictions about the next developmental stages
           5. Report should be written in Korean.
           6. The number of characters must be less than 250 characters.
           7. The answer must be a json value containing:
            "content": "paragraph content in Korean", "growthDegree": X, "maxDegree": 10
            """
    );

    public long append(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        Child child = childReader.read(childId);
        int monthsOld = childReader.readMonthsOld(childId);

        double birthWeight = child.getBirthWeight();
        String growthData = jsonObjectMapper.toJson(growthContentReader.readAllByMonth(child.getId(), monthsOld));
        List<MilestoneStateDetail> milestone = milestoneStateDetailReader.read(memberId, childId, monthsOld);
        Prompt prompt = promptTemplate.create(Map.of(
                "birthWeight", birthWeight,
                "growth", growthData,
                "milestone", milestone
        ));
        ReportCreatedByAi createdReport = creatorUsingOpenAi.create(prompt, ReportCreatedByAi.class);

        Report report = Report.builder()
                .childId(childId)
                .month(monthsOld)
                .content(createdReport.getContent())
                .growthDegree(createdReport.getGrowthDegree())
                .maxDegree(createdReport.getMaxDegree())
                .createdAt(LocalDateTime.now())
                .build();
        reportRepository.save(report);
        return report.getId();
    }
}
