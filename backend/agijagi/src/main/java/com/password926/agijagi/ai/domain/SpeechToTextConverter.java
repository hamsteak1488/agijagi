package com.password926.agijagi.ai.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SpeechToTextConverter {

    private final ResourceLoader resourceLoader;
    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioTranscriptionOptions transcriptionOptions =
            OpenAiAudioTranscriptionOptions.builder()
                    .withLanguage("ko")
                    .withPrompt("Ask not this, but ask that")
                    .withTemperature(0f)
                    .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.JSON)
                    .build();

    // TODO: 음성 파일을 어디에 저장할지 정해야함. 메모리 vs 디스크 vs 클라우드
    public String convertSpeechToText(File audioFile) throws IOException {

        Resource audioResource = resourceLoader.getResource(audioFile.getAbsolutePath());
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioResource, transcriptionOptions);
        return openAiAudioTranscriptionModel.call(transcriptionRequest)
                .getResults()
                .get(0)
                .getOutput();
    }
}
