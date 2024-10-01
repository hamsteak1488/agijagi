package com.password926.agijagi.story.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.diary.entity.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class StoryGPT {

    private final OpenAiChatModel openAiChatModel;

    private static final ObjectMapper objectMapper =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public String getCreateStoryDtoFromQuery(List<Diary> diaries, String name, Long age){
        List<String> diaryData = new ArrayList<>();
        for (Diary diary : diaries){
            diaryData.add(diary.getContent());
        }
        String data = convertValuesToJson(diaryData);
        PromptTemplate promptTemplate = new PromptTemplate(
                """
                It has the task of creating a fairy tale based on the child's growth process using childcare diary data. This fairy tale should capture the child's growth process and the essence of a memorable moment in an ingenious and attractive story.

                First, the following information is provided:

                <child_name>
                (child_name : {child_name})
                </child_name>

                ‹parenting_diary>
                ({data})
                </parenting_diary>

                <child_age>
                (child_age : {child_age})
                </child_age>

                Use this information to create a fairy tale that incorporates major events and milestones in your parenting diary.
                Follow these instructions:

                1. "There used to be a kid named [child_name]... " the story begins
                2. Use your child's age to determine the appropriate length and complexity of the story.
                3. Translate real events from parenting diaries into magical or fantasy scenarios.
                4. Include character growth and development that reflects your child's actual progress.
                5. It should be generated up to 10 sentences. Before that, I need to finish the story.
                6. No more than 50 characters per sentence.
                7. Fairy tales should be written in Korean.
                8. Paragraphs should be written as examples below.
                [{z}"pageNumber": 1, "content": "content1"{a}, {z}"pageNumber": 2,"content": "content2"{a}]
                """);
        Prompt prompt = promptTemplate.create(Map.of("child_name", name, "child_age", age, "data", data, "a", "}", "z", "{"));
        String response = openAiChatModel.call(prompt).getResult().getOutput().getContent();
        System.out.println(response);
//        return convertJsonToObject(response, StoryPage.class);
        return response;
    }

    private String convertValuesToJson(Object values) {
        try {
            return objectMapper.writeValueAsString(values);

        } catch (JsonProcessingException e) {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private <T> T convertJsonToObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }
    }

    private String formatToJson(String string) {
        return string.substring(7, string.length() - 3);
    }
}
