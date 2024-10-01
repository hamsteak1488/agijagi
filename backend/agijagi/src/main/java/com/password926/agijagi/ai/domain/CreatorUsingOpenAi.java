package com.password926.agijagi.ai.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreatorUsingOpenAi {

    private final OpenAiChatModel openAiChatModel;

    public <T> T create(Prompt prompt, Class<T> clazz) {
        String response = openAiChatModel.call(prompt).getResult().getOutput().getContent();
        return JsonObjectMapper.fromJson(JsonFormatter.format(response), clazz);
    }
}
