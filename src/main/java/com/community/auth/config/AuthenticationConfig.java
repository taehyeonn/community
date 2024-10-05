package com.community.auth.config;

import com.community.global.auth.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/v1/members/test"); //todo: test용 임시 메서드
    }
}
