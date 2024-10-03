package com.password926.agijagi.member.infrastructure;

import com.password926.agijagi.member.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
    Optional<Member> findByIdAndIsDeletedIsFalse(long id);
    Optional<Member> findByEmailAndIsDeletedIsFalse(String email);
}
