package com.password926.agijagi.ai.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;

@RequiredArgsConstructor
@Component
public class SpeechToTextConverter {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;
    private final OpenAiAudioTranscriptionOptions transcriptionOptions =
            OpenAiAudioTranscriptionOptions.builder()
                    .withLanguage("ko")
                    .withPrompt("Ask not this, but ask that")
                    .withTemperature(0f)
                    .withResponseFormat(OpenAiAudioApi.TranscriptResponseFormat.JSON)
                    .build();

    public String convert(Base64Content base64Content) {
        File tempFile = Base64ToFileConverter.convert(base64Content);
        try {
            Resource audioResource = new FileSystemResource(tempFile);
            AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioResource, transcriptionOptions);
            return openAiAudioTranscriptionModel.call(transcriptionRequest)
                    .getResults()
                    .get(0)
                    .getOutput();
        } finally {
            FileRemover.remove(tempFile);
        }
    }
}
