package com.password926.agijagi.child.service;

import com.password926.agijagi.child.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ChildService {

    private final ChildDetailReader childDetailReader;
    private final FollowerReader followerReader;
    private final ChildAppender childAppender;
    private final InvitationCodeReader invitationCodeReader;
    private final FollowerAppender followerAppender;
    private final InvitationCodeCreator invitationCodeCreator;
    private final ChildRemover childRemover;
    private final ChildUpdater childUpdater;
    private final FollowerUpdater followerUpdater;
    private final ChildImageUpdater childImageUpdater;
    private final ChildImageRemover childImageRemover;
    private final FollowerRemover followerRemover;

    public ChildDetail readChildDetail(long memberId, long childId) {
        return childDetailReader.readByMemberAndChild(memberId, childId);
    }

    public List<ChildDetail> readChildDetailsByMember(long memberId) {
        return childDetailReader.readByMember(memberId);
    }

    public List<Follower> readFollowers(long memberId, long childId) {
        return followerReader.readFollowers(memberId, childId);
    }

    public void appendChild(
            long memberId,
            ChildContent childContent,
            MultipartFile image
    ) {
        childAppender.append(memberId, childContent, image);
    }

    public void appendFollower(long memberId, UUID invitationCodeId) {
        long childId = invitationCodeReader.read(invitationCodeId)
                .getChildId();
        followerAppender.append(memberId, childId, Authority.READ);
    }

    public UUID createInvitationCode(long memberId, long childId) {
        return invitationCodeCreator.create(memberId, childId);
    }

    public void removeChild(long memberId, long childId) {
        childRemover.remove(memberId, childId);
    }

    public void updateChild(
            long memberId,
            long childId,
            ChildContent childContent
    ) {
        childUpdater.update(memberId, childId, childContent);
    }

    public void updateFollower(
            long memberId,
            long childId,
            long followerId,
            Authority authority
    ) {
        followerUpdater.update(memberId, childId, followerId, authority);
    }

    public void updateChildImage(
            long memberId,
            long childId,
            MultipartFile image
    ) {
        childImageUpdater.update(memberId, childId, image);
    }

    public void removeChildImage(long memberId, long childId) {
        childImageRemover.remove(memberId, childId);
    }

    public void removeFollower(
            long memberId,
            long childId,
            long followerId
    ) {
        followerRemover.remove(memberId, childId, followerId);
    }
}
