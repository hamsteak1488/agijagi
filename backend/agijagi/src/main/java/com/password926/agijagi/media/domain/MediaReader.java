package com.password926.agijagi.media.domain;

import com.password926.agijagi.media.infrastructure.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MediaReader {
    private final MediaRepository mediaRepository;

    @Transactional(readOnly = true)
    public List<String> getMediaUrls(List<UUID> mediaIds) {
        return mediaRepository.findUrlByIdIn(mediaIds);
    }
}
