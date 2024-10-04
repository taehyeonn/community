package com.community.global.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties("jwt")
@ConfigurationPropertiesBinding
public record JwtProperties(
        String secret,
        Long maxAge,
        Long refreshMaxAge
) {

}
