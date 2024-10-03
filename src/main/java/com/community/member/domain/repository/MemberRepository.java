package com.community.member.domain.repository;

import com.community.member.domain.Member;
import com.community.member.domain.vo.Email;

public interface MemberRepository {

    Member save(Member member);

    boolean existsMemberByEmail(Email email);
}
