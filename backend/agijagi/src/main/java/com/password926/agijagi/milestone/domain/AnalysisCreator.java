package com.password926.agijagi.milestone.domain;

import com.password926.agijagi.child.domain.ChildValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AnalysisCreator {

    private final ChildValidator childValidator;
    private final OpenAiChatModel openAiChatModel;

    public void create(long memberId, long childId) {
        childValidator.validateWriteAuthority(memberId, childId);
        // 필요한 데이터 가져오기
        // 생성
    }
}
