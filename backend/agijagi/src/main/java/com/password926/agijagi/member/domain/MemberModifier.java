package com.password926.agijagi.member.domain;

import com.password926.agijagi.common.errors.errorcode.CommonErrorCode;
import com.password926.agijagi.common.errors.exception.RestApiException;
import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.media.domain.MediaStorage;
import com.password926.agijagi.media.domain.Image;
import com.password926.agijagi.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberModifier {
    private final MemberRepository memberRepository;
    private final MemberReader memberReader;
    private final MediaStorage mediaStorage;

    @Transactional
    public void modifyProfileDetail(long memberId, ProfileDetail profileDetail) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND))
                .updateProfileDetail(profileDetail);
    }

    @Transactional
    public void modifyProfileImage(long memberId, MediaResource mediaResource) {
        Member member = memberReader.read(memberId);
        Image storedImage = mediaStorage.storeImage(mediaResource);
        member.updateProfileImage(storedImage);
    }
}
