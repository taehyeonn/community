package com.community.auth.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationExtractor {

    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_TYPE = "Bearer ";

    public static String extractToken(final HttpServletRequest request) {
        return getAuthorizationHeader(request.getHeader(AUTH_HEADER));
    }

    private static String getAuthorizationHeader(final String authorization) {
        if (authorization == null || !authorization.startsWith(AUTH_TYPE)) {
            throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
        }
        return authorization.substring(AUTH_TYPE.length());
    }
}
