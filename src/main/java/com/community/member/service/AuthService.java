package com.community.member.service;


import com.community.member.controller.dto.LoginRequest;
import com.community.member.controller.dto.JwtAuthToken;
import com.community.global.auth.JwtProvider;
import com.community.member.domain.repository.MemberRepository;
import com.community.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public JwtAuthToken login(final LoginRequest request) {
        if (!memberRepository.existsMemberByEmail(Email.from(request.email()))) {
            throw new IllegalArgumentException("멤버를 조회할 수 없습니다.");
        }

        Map<String, Object> payload = Map.of("role", "member");
        String accessToken = jwtProvider.generateToken(request.email(), payload);
        return JwtAuthToken.builder()
                .accessToken(accessToken)
                .build();
    }
}
