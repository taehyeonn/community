package com.community.member.domain.repository;

import com.community.member.domain.Member;
import com.community.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository jpaRepository;

    @Override
    public Member save(Member member) {
        return jpaRepository.save(member);
    }

    @Override
    public boolean existsMemberByEmail(Email email) {
        return jpaRepository.existsMemberByEmail(email);
    }
}
