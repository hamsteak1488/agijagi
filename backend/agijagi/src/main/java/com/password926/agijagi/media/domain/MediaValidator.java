package com.password926.agijagi.media.domain;

import org.springframework.stereotype.Component;

@Component
public class MediaValidator {
    public boolean validateImage(MediaResource mediaResource) {
        return mediaResource.getContentType().startsWith("image/");
    }

    public boolean validateVideo(MediaResource mediaResource) {
        return mediaResource.getContentType().startsWith("video/");
    }

    public boolean validateAny(MediaResource mediaResource) {
        return validateImage(mediaResource) || validateVideo(mediaResource);
    }
}
