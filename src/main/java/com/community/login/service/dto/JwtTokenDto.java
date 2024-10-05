package com.community.login.service.dto;

import com.community.login.controller.dto.AccessToken;
import lombok.Builder;
import org.springframework.http.ResponseCookie;

@Builder
public record JwtTokenDto(AccessToken accessToken, ResponseCookie refreshTokenCookie) {

}
