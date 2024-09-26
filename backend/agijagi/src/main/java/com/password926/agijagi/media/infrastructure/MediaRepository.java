package com.password926.agijagi.media.infrastructure;

import com.password926.agijagi.media.domain.Media;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MediaRepository extends Repository<Media, Long> {
    Media save(Media media);
    Optional<Media> findById(long id);
}
