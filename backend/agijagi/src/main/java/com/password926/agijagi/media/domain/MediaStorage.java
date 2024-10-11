package com.password926.agijagi.media.domain;

import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.controller.MediaErrorCode;
import com.password926.agijagi.media.infrastructure.MediaRepository;
import com.password926.agijagi.media.infrastructure.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaStorage {
    private final S3Manager s3Manager;
    private static final String S3_KEY_PREFIX = "media/";
    private final MediaValidator mediaValidator;

    @Transactional
    public Image storeImage(MediaResource mediaResource) {
        mediaValidator.validateImage(mediaResource);

        UUID generatedUUID = UUID.randomUUID();
        String uploadUrl = store(generatedUUID, mediaResource);

        return Image.builder()
                .id(generatedUUID)
                .url(uploadUrl)
                .build();
    }

    @Transactional
    public Video storeVideo(MediaResource mediaResource) {
        mediaValidator.validateVideo(mediaResource);

        UUID generatedUUID = UUID.randomUUID();
        String uploadUrl = store(generatedUUID, mediaResource);

        return Video.builder()
                .id(generatedUUID)
                .url(uploadUrl)
                .build();
    }

    @Transactional
    public Media storeAny(MediaResource mediaResource) {
        if (mediaValidator.isImage(mediaResource)) {
            return storeImage(mediaResource);
        }
        if (mediaValidator.isVideo(mediaResource)) {
            return storeVideo(mediaResource);
        }

        throw new RestApiException(MediaErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }

    private String store(UUID key, MediaResource mediaResource) {
        return s3Manager.upload(S3_KEY_PREFIX + key, mediaResource);
    }

    @Transactional
    public void removeMedia(UUID mediaId) {
        // TODO: 미디어 삭제
    }
}
