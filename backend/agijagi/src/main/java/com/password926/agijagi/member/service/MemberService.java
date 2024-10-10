package com.password926.agijagi.member.service;

import com.password926.agijagi.media.domain.MediaResource;
import com.password926.agijagi.member.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRegistry memberRegistry;
    private final MemberUpdater memberUpdater;
    private final MemberRemover memberRemover;
    private final MemberDetailReader memberDetailReader;

    public long registerMember(String email, String password, String nickname, MediaResource profileImage) {
        return memberRegistry.register(email, password, nickname, profileImage);
    }

    public void updateMember(long memberId, String email, String password, String nickname) {
        memberUpdater.update(memberId, email, password, nickname);
    }

    public void updateMemberProfileImage(long memberId, MediaResource profileImage) {
        memberUpdater.updateProfileImage(memberId, profileImage);
    }

    public void deleteMemberProfileImage(long memberId) {
        memberUpdater.deleteProfileImage(memberId);
    }

    public void removeMember(long memberId) {
        memberRemover.remove(memberId);
    }

    public MemberDetail getMemberDetail(long memberId) {
        return memberDetailReader.read(memberId);
    }
}
