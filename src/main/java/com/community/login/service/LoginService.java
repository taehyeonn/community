package com.community.login.service;


import com.community.global.auth.JwtProvider;
import com.community.login.controller.dto.AccessToken;
import com.community.login.service.dto.JwtTokenDto;
import com.community.login.controller.dto.LoginRequest;
import com.community.login.controller.dto.RefreshAccessToken;
import com.community.member.domain.Member;
import com.community.member.domain.repository.MemberRepository;
import com.community.member.domain.vo.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public JwtTokenDto login(final LoginRequest request) {
        Member member = memberRepository.findMemberByEmail(Email.from(request.email()))
                .orElseThrow(() -> new IllegalArgumentException("회원을 조회할 수 없습니다."));

        String accessToken = jwtProvider.generateToken(member.getId());
        ResponseCookie refreshTokenCookie = jwtProvider.generateRefreshToken(member.getId());
        return JwtTokenDto.builder()
                .accessToken(new AccessToken(accessToken))
                .refreshTokenCookie(refreshTokenCookie)
                .build();
    }

    public RefreshAccessToken refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Long memberId = Long.valueOf(jwtProvider.getMemberId(refreshToken));
            String newAccessToken = jwtProvider.generateToken(memberId);
            return new RefreshAccessToken(newAccessToken);
        } else {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
    }
}
