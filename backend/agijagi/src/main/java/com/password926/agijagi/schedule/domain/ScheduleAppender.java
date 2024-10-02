package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.ai.domain.Base64Content;
import com.password926.agijagi.ai.domain.CreatorUsingOpenAi;
import com.password926.agijagi.ai.domain.SpeechToTextConverter;
import com.password926.agijagi.child.domain.ChildReader;
import com.password926.agijagi.child.domain.ChildValidator;
import com.password926.agijagi.member.domain.MemberReader;
import com.password926.agijagi.schedule.infrastructure.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class ScheduleAppender {

    private final MemberReader memberReader;
    private final ChildReader childReader;
    private final ChildValidator childValidator;
    private final ScheduleRepository scheduleRepository;
    private final SpeechToTextConverter speechToTextConverter;
    private final CreatorUsingOpenAi creatorUsingOpenAi;

    public void append(
            long memberId,
            long childId,
            ScheduleContent scheduleContent
    ) {
        childValidator.validateWriteAuthority(memberId, childId);
        scheduleRepository.save(Schedule.builder()
                .member(memberReader.read(memberId))
                .child(childReader.read(childId))
                .startDateTime(scheduleContent.getStartDateTime())
                .endDateTime(scheduleContent.getEndDateTime())
                .title(scheduleContent.getTitle())
                .description(scheduleContent.getDescription())
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    public void appendVoice(
            long memberId,
            long childId,
            Base64Content base64Content
    ) {
        childValidator.validateWriteAuthority(memberId, childId);

        String text = speechToTextConverter.convert(base64Content);
        Prompt prompt = promptTemplate.create(Map.of(
                "now", LocalDateTime.now(),
                "text", text));
        ScheduleCreatedByAi scheduleCreatedByAi = creatorUsingOpenAi.create(prompt, ScheduleCreatedByAi.class);

        scheduleRepository.save(Schedule.builder()
                .member(memberReader.read(memberId))
                .child(childReader.read(childId))
                .startDateTime(scheduleCreatedByAi.getStartDateTime())
                .endDateTime(scheduleCreatedByAi.getEndDateTime())
                .title(scheduleCreatedByAi.getTitle())
                .description(scheduleCreatedByAi.getDescription())
                .createdAt(LocalDateTime.now())
                .build()
        );
    }

    private final PromptTemplate promptTemplate = new PromptTemplate(
            """
            Convert to json so that users can register their schedules with text.
            
            First, the following information is provided:
            
            <now>
            now: {now}
            </now>
            
            <text>
            text : {text}
            </text>
            
            Follow these instructions:
            
            1. The answer must be a json value containing:
               "startDateTime": , "endDateTime": , "title": , "description":
            2. "title" and "description" must be written in Korean.
            3. "title" and "description" clearly reflect "text".
            4. "startDateTime" and "endDateTime" must be in 'YYYY-MM-DDTHH:mm:ss' format.
            5. "startDateTime" is "now" if you can't figure it out by "text".
            6. If "endDateTime" cannot be identified by "text", it is an hour ahead of "startDateTime".
            """
    );
}
