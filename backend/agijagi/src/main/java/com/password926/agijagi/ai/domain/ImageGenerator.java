package com.password926.agijagi.ai.domain;

import com.password926.agijagi.ai.infrastructure.AiImageModel;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class ImageGenerator {

    private final AiImageModel aiImageModel;
    private final MediaStorage mediaStorage;

    public List<Image> generate(List<String> messages) {
        List<CompletableFuture<String>> futures = messages.stream()
                .map(aiImageModel::generateAsync)
                .toList();

        List<String> base64Images = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        return base64Images.stream()
                .map(base64Image -> new Base64Content("temp", "png", base64Image))
                .map(Base64ToFileConverter::convert)
                .map(this::storeGeneratedImage)
                .toList();
    }

    private Image storeGeneratedImage(File tempFile) {
        try {
            Resource imageResource = new FileSystemResource(tempFile);
            MediaResource mediaResource = new MediaResource(imageResource, "image/png", Map.of());
            return mediaStorage.storeImage(mediaResource);
        } finally {
            FileRemover.remove(tempFile);
        }
    }
}
