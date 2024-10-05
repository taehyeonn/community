package com.community.login.service;


import com.community.global.auth.JwtProvider;
import com.community.login.controller.dto.JwtAuthToken;
import com.community.login.controller.dto.LoginRequest;
import com.community.member.domain.repository.MemberRepository;
import com.community.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public JwtAuthToken login(final LoginRequest request) {
        Member member = memberRepository.findMemberByEmail(Email.from(request.email()))
                .orElseThrow(() -> new IllegalArgumentException("회원을 조회할 수 없습니다."));

        Map<String, Object> payload = Map.of("role", "member");
        String accessToken = jwtProvider.generateToken(member.getId(), payload);
        String refreshToken = jwtProvider.generateRefreshToken();
        return JwtAuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
