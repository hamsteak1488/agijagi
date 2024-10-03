package com.password926.agijagi.member.domain;

import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.member.controller.MemberErrorCode;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRegistry {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MediaStorage mediaStorage;

    @Transactional
    public long register(String email, String password, String nickname, MediaResource profileImage) {
        memberRepository.findByEmailAndIsDeletedIsFalse(email)
                .ifPresent(member -> { throw new RestApiException(MemberErrorCode.EMAIL_DUPLICATED); } );

        Image image = profileImage != null ? mediaStorage.storeImage(profileImage) : null;
        String encodePassword = bCryptPasswordEncoder.encode(password);

        Member newMember = Member.createMember(email, encodePassword, nickname, image);
        memberRepository.save(newMember);

        return newMember.getId();
    }
}
