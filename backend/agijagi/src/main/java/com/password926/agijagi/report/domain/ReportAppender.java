package com.password926.agijagi.report.domain;

import com.password926.agijagi.ai.domain.CreatorUsingOpenAi;
import com.password926.agijagi.ai.domain.JsonObjectMapper;
import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.growth.domain.GrowthContentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ReportAppender {

    private final ChildValidator childValidator;
    private final ChildReader childReader;
    private final GrowthContentReader growthContentReader;
    private final CreatorUsingOpenAi creatorUsingOpenAi;
    private final JsonObjectMapper jsonObjectMapper;
    private final PromptTemplate promptTemplate = new PromptTemplate(
            """
            
            """
    );

    public void append(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);

    }

    private ReportCreatedByAi createReport(long childId) {
        Child child = childReader.read(childId);
        String growthData = jsonObjectMapper.toJson(
                growthContentReader.readAllByMonth(child.getId(), childReader.readMonthsOld(childId))
        );
        double birthWeight = child.getBirthWeight();

        Prompt prompt = promptTemplate.create(Map.of("growth", growthData, "birthWeight", birthWeight));
        return creatorUsingOpenAi.create(prompt, ReportCreatedByAi.class);
    }
}
