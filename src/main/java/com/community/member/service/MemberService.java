package com.community.member.service;

import com.community.member.controller.dto.MemberSignUpRequest;
import com.community.member.domain.Member;
import com.community.member.domain.repository.MemberRepository;
import com.community.member.service.dto.MemberDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto create(MemberSignUpRequest request) {
        Member member = Member.of(request.email(), request.password(), passwordEncoder, memberRepository);
        return MemberDto.from(memberRepository.save(member));
    }
}
