package com.password926.agijagi.member.infrastructure;

import com.password926.agijagi.member.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
    void delete(Member member);
    void deleteById(long id);
    Optional<Member> findById(long id);
    Optional<Member> findByProfileDetailEmail(String email);
}
