package com.password926.agijagi.common;

import com.password926.agijagi.child.domain.ChildAppender;
import com.password926.agijagi.child.domain.ChildContent;
import com.password926.agijagi.child.domain.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ChildAppender childAppender;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createChild();
    }

    private void createChild() {
        ChildContent childContent = ChildContent.builder()
                .name("김다운")
                .nickname("아기다운")
                .gender(Gender.MALE)
                .birthWeight(3.5)
                .birthHeight(51.2)
                .birthday(LocalDate.of(2023,9,1))
                .build();
        childAppender.append(1, childContent, null);
    }
}
