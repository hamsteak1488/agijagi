package com.password926.agijagi.member.domain;

import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberUpdater {
    private final MemberRepository memberRepository;
    private final MemberReader memberReader;
    private final MediaStorage mediaStorage;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void update(long memberId, String email, String password, String nickname) {
        Member member = memberReader.read(memberId);
        String encodePassword = password != null ? bCryptPasswordEncoder.encode(password) : null;

        member.update(email, encodePassword, nickname);
    }

    @Transactional
    public void updateProfileImage(long memberId, MediaResource mediaResource) {
        Member member = memberReader.read(memberId);
        Image storedImage = mediaStorage.storeImage(mediaResource);
        member.updateProfileImage(storedImage);
    }
}
