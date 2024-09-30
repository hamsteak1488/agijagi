package com.password926.agijagi.member.service;

import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.member.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberReader memberReader;
    private final MemberRegistry memberRegistry;
    private final MemberModifier memberModifier;
    private final MemberRemover memberRemover;

    public long registerMember(ProfileDetail profileDetail, String password) {
        return memberRegistry.register(profileDetail, password);
    }

    public ProfileDetail getMemberProfileDetail(long memberId) {
        return memberReader.readProfileDetail(memberId);
    }

    public void modifyMemberProfileDetail(long memberId, ProfileDetail profileDetail) {
        memberModifier.modifyProfileDetail(memberId, profileDetail);
    }

    public void modifyMemberProfileImage(long memberId, MediaResource mediaResource) {
        memberModifier.modifyProfileImage(memberId, mediaResource);
    }

    public void removeMember(long memberId) {
        memberRemover.remove(memberId);
    }
}
