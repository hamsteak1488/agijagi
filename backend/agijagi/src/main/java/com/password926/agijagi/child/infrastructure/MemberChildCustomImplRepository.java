package com.password926.agijagi.child.infrastructure;

import static com.password926.agijagi.child.domain.QMemberChild.memberChild;
import static com.password926.agijagi.member.domain.QMember.member;
import static com.password926.agijagi.media.domain.QImage.image;
import static com.password926.agijagi.media.domain.QMedia.media;

import com.password926.agijagi.child.domain.Follower;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberChildCustomImplRepository implements MemberChildCustomRepository{

    private final JPAQueryFactory query;

    @Override
    public List<Follower> findFollowers(long childId) {
        return query
                .select(Projections.constructor(Follower.class,
                        member.id,
                        memberChild.child.id,
                        member.email,
                        member.nickname,
                        media.url))
                .from(memberChild)
                .leftJoin(member).on(member.id.eq(memberChild.member.id))
                .leftJoin(media).on(media.id.eq(member.profileImage.id))
                .where(memberChild.child.id.eq(childId))
                .fetch();
    }
}
