package com.community.member.domain.repository;

import com.community.member.domain.Member;
import com.community.member.domain.vo.Email;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    boolean existsMemberByEmail(Email email);

    Optional<Member> findMemberByEmail(Email email);
}
