package com.password926.agijagi.media.infrastructure;

import com.password926.agijagi.media.domain.Media;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends Repository<Media, Long> {
    Media save(Media media);
    Optional<Media> findById(UUID id);

    @Query("SELECT m.url FROM Media as m WHERE m.id IN :ids")
    List<String> findUrlByIdIn(List<UUID> ids);
}
