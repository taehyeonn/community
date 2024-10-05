package com.community.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_TYPE = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        final String authorization = request.getHeader(AUTH_HEADER);
        final String token = extractToken(authorization);

        String memberId = jwtProvider.getMemberId(token);
        System.out.println("memberId = " + memberId);

        return true;
    }

    private String extractToken(final String authorization) {
        if (authorization == null || !authorization.startsWith(AUTH_TYPE)) {
            throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
        }
        return authorization.substring(AUTH_TYPE.length());
    }
}
