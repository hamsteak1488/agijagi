package com.password926.agijagi.child.infrastructure;

import com.password926.agijagi.child.domain.Follower;

import java.util.List;

public interface MemberChildCustomRepository {

    List<Follower> findFollowers(long childId);
}
