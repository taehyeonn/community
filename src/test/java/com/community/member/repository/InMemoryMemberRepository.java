package com.community.member.repository;

import com.community.member.domain.Member;
import com.community.member.domain.repository.MemberRepository;
import com.community.member.domain.vo.Email;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();

    @Override
    public Member save(Member member) {
        Long id = (long) (members.size() + 1);
        members.put(id, member);
        return member;
    }

    @Override
    public boolean existsMemberByEmail(Email email) {
        return members.values()
                .stream()
                .anyMatch(member -> member.getEmail().getValue().equals(email.getValue()));
    }

    @Override
    public Optional<Member> findMemberByEmail(Email email) {
        return members.values()
                .stream()
                .filter(member -> member.getEmail().getValue().equals(email.getValue()))
                .findFirst();
    }
}
