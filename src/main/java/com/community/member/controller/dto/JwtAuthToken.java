package com.community.member.controller.dto;

import lombok.Builder;

@Builder
public record JwtAuthToken(String accessToken, String refreshToken) {

}
