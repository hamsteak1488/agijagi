package com.password926.agijagi.child.infrastructure;

import com.password926.agijagi.child.domain.MemberChild;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberChildRepository extends Repository<MemberChild, Long> {

    Optional<MemberChild> findByMemberIdAndChildId(long memberId, long childId);

    List<MemberChild> findByMemberId(long memberId);

    long countByChildId(long childId);

    void save(MemberChild memberChild);

    boolean existsByMemberIdAndChildId(long memberId, long childId);

    void deleteAllByChildId(long childId);

    void delete(MemberChild memberChild);
}
