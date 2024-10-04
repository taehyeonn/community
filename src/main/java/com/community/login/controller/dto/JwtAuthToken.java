package com.community.login.controller.dto;

import lombok.Builder;

@Builder
public record JwtAuthToken(String accessToken, String refreshToken) {

}
