package com.community.member.domain.repository;

import com.community.member.domain.Member;
import com.community.member.domain.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(Email email);

    Optional<Member> findMemberByEmail(Email email);
}
