package com.password926.agijagi.media.domain;

import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.controller.MediaErrorCode;
import com.password926.agijagi.media.entity.Image;
import com.password926.agijagi.media.infrastructure.MediaRepository;
import com.password926.agijagi.media.infrastructure.S3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MediaStorage {
    private final S3Manager s3Manager;
    private final MediaRepository mediaRepository;
    private static final String S3_KEY_PREFIX = "abc/";

    @Transactional
    public Image storeImage(Resource resource, String contentType) {
        if (!contentType.startsWith("image/")) {
            throw new RestApiException(MediaErrorCode.UNSUPPORTED_MEDIA_TYPE);
        }
        Image image = Image.builder().build();
        mediaRepository.save(image);
        image.updateUrl(s3Manager.upload(S3_KEY_PREFIX + image.getId(), resource, contentType));

        return image;
    }

    @Transactional
    public void removeMedia(long mediaId) {
        // TODO: 미디어 삭제
    }
}
