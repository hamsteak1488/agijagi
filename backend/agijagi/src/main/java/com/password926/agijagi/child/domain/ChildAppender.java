package com.password926.agijagi.child.domain;

import com.password926.agijagi.child.infrastructure.ChildRepository;
import com.password926.agijagi.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ChildAppender {

    private final ChildRepository childRepository;
    private final MemberChildAppender memberChildAppender;

    @Transactional
    public void append(long memberId, ChildContent childContent, MultipartFile image) {
        //TODO: s3 구현시 로직 추가
        String imageUrl = null;
        Child child = Child.builder()
                .name(childContent.getName())
                .nickname(childContent.getNickname())
                .birthday(childContent.getBirthday())
                .build();
        childRepository.save(child);

        //TODO: appendChild, member 로직 수정
        Member member = Member.of(null, null);
        memberChildAppender.createRelation(member, child, "WRITE");
    }
}
