package com.password926.agijagi.media.domain;

import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.controller.MediaErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class MediaResource {
    private final Resource resource;
    private final String contentType;
    private final Map<String, Object> attributes;

    public static MediaResource from(MultipartFile multipartFile) {
        if (multipartFile.getContentType() == null) {
            throw new RestApiException(MediaErrorCode.UNKNOWN_MEDIA_TYPE);
        }

        return new MediaResource(multipartFile.getResource(), multipartFile.getContentType(), Map.of());
    }
}
