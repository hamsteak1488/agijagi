package com.password926.agijagi.schedule.domain;

import com.password926.agijagi.child.domain.Child;
import com.password926.agijagi.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public void update(ScheduleContent scheduleContent) {
        this.startDateTime = scheduleContent.getStartDateTime();
        this.endDateTime = scheduleContent.getEndDateTime();
        this.title = scheduleContent.getTitle();
        this.description = scheduleContent.getDescription();
    }
}
