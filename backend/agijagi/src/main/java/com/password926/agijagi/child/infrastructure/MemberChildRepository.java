package com.password926.agijagi.child.infrastructure;

import com.password926.agijagi.child.domain.MemberChild;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MemberChildRepository extends Repository<MemberChild, Long> {

    List<MemberChild> findByMemberId(long memberId);

    long countByChildId(long childId);
}
