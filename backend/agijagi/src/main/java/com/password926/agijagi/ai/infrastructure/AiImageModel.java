package com.password926.agijagi.ai.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class AiImageModel {

    private final OpenAiImageModel openAiImageModel;
    private final OpenAiImageOptions openAiImageOptions = OpenAiImageOptions.builder()
            .withQuality("hd")
            .withN(1)
            .withHeight(1024)
            .withWidth(1024)
            .withResponseFormat("b64_json")
            .build();

    @Async
    public CompletableFuture<String> generateAsync(String message) {
        ImageResponse response = openAiImageModel.call(new ImagePrompt(message, openAiImageOptions));
        return CompletableFuture.completedFuture(response.getResult().getOutput().getB64Json());
    }
}
