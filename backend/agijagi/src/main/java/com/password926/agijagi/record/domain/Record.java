package com.password926.agijagi.record.domain;

import com.password926.agijagi.child.domain.Child;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childId", nullable = false)
    private Child child;

    @Convert(converter = RecordTypeConverter.class)
    @Column(nullable = false)
    private RecordType type;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
