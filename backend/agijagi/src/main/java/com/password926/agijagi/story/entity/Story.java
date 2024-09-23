package com.password926.agijagi.story.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long childId;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private LocalDate startTime;

    @Column(nullable = false)
    private LocalDate endTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Boolean isDeleted;

    public void remove() {
        isDeleted = true;
    }

}
