package com.password926.agijagi.media.domain;

import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.controller.MediaErrorCode;
import org.springframework.stereotype.Component;

@Component
public class MediaValidator {
    public boolean isImage(MediaResource mediaResource) {
        return mediaResource.getContentType().startsWith("image/");
    }

    public boolean isVideo(MediaResource mediaResource) {
        return mediaResource.getContentType().startsWith("video/");
    }

    public void validateImage(MediaResource mediaResource) {
        if (!isImage(mediaResource)) {
            throw new RestApiException(MediaErrorCode.INVALID_IMAGE_TYPE);
        }
    }

    public void validateVideo(MediaResource mediaResource) {
        if (!isVideo(mediaResource)) {
            throw new RestApiException(MediaErrorCode.INVALID_VIDEO_TYPE);
        }
    }

    public boolean validateAny(MediaResource mediaResource) {
        return isImage(mediaResource) || isVideo(mediaResource);
    }
}
